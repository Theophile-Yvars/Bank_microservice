from sklearn.tree import DecisionTreeClassifier, export_text, export_graphviz, plot_tree
from app.db_proxy.db_proxy import DB_proxy
from app.kafka.kafka import Kafka
import requests
from app.dto.analyseDto import AnalysisDTO
from app.dto.productDto import ProductDTO

class DecisionTree():
    def __init__(self):
        print("INIT DECISION TREE", flush=True)
        self.kafka = Kafka()
        # Initialisez le modèle
        self.model = DecisionTreeClassifier()
        self.db_proxy = DB_proxy()
        print("TRAIN MODEL .....")
        self.__train_decision_tree()

    async  def run(self):
        print("RUN DECISION TREE ...")
        # 1 je recupere les datas avec un filtre
        # En gros, je veux pour chaque client, le nombre de transaction à l'etrangé, le nombre de transaction par internet, etc ....
        client_map = self.__init_datas_for_decision_algo()
        datas = self.__armonisation(client_map)
        print("DATA : ", datas, flush=True)
        product_dto_list = self.__get_products_from_banker()

        for key, value in datas.items():
            print("Client : ", key)
            prediction = self.__predict_decision_tree([value])
            print(prediction)
            analyseDtoList = self.__get_offers(prediction, product_dto_list, key)
            await self.__send_Product(analyseDtoList)

    async def __send_Product(self, analyseDtoList):
        for analyseDto in analyseDtoList:
            await self.kafka.produce("primabankoffers", analyseDto)

    def __get_products_from_banker(self):
        url = "http://banker-service:9091/bankers/getProduces"  # Remplacez par l'adresse de votre serveur Java

        try:
            response = requests.get(url)
            response.raise_for_status()  # Lèvera une exception en cas d'erreur HTTP
            products = response.json()
            print(products)
            product_dto_list = [ProductDTO(item['id'], item['name'], item['description'], item['price'], item['mode'])
                                for
                                item in products]
            return product_dto_list
        except requests.exceptions.RequestException as err:
            print(f"Erreur lors de la requête : {err}")
            return None

    def __get_offer_by_name(self, offers, name):
        for offer in offers:
            if offer.name == name:
                return offer
        return None

    def __get_offers(self, prediction, offers, id_client):
        # offre : [TravelCard, TravelCardGold, VirtualCard, CarteCreditProfessionnel]
        analyseDtoList = []

        #TravelCard
        if prediction[0] == '0':
            print("Pas de Travel Card")
        else:
            print("Travel Card")
            product = self.__get_offer_by_name(offers, 'TravelCard')
            analyseDtoList.append(AnalysisDTO(id_client, product))

        # TravelCardGold
        if prediction[1] == '0':
            print("Pas de Travel Card Gold")
        else:
            print("Travel Card Gold")
            product = self.__get_offer_by_name(offers, 'TravelCardGold')
            analyseDtoList.append(AnalysisDTO(id_client, product))

        # VirtualCard
        if prediction[2] == '0':
            print("Pas de Virtual Card")
        else:
            print("Virtual Card")
            product = self.__get_offer_by_name(offers, 'Virtual Card')
            analyseDtoList.append(AnalysisDTO(id_client, product))

        # CarteCreditProfessionnel
        if prediction[3] == '0':
            print("Pas de Carte Credit Professionnel")
        else:
            print("Carte Credit Professionnel")
            product = self.__get_offer_by_name(offers, 'Carte Crédit Professionnel')
            analyseDtoList.append(AnalysisDTO(id_client, product))

        return analyseDtoList

    def __armonisation(self, client_map):
        data = {}
        print("Client : x : [ france, particulier, entreprise, direct ]")
        for client_id, counts in client_map.items():
            data[client_id] = [0, 0, 0, 0] # france, particulier, entreprise, direct

            total = counts[-1]
            france_percentage = (counts[0] * 100) / total
            direct_percentage = (counts[3] * 100) / total

            data[client_id][0] = int(france_percentage)
            data[client_id][1] = counts[1]
            data[client_id][2] = counts[2]
            data[client_id][3] = int(direct_percentage)

            print("Client : ", client_id, " : ", data[client_id])

        return data


    def __init_datas_for_decision_algo(self):
        # Initialiser la map pour stocker les données par client
        client_map = {}

        # Récupérer toutes les transactions de la base de données
        transactions = self.db_proxy.get_transactions()

        # Parcourir chaque transaction
        for transaction in transactions: # (1, 1, 'other', 'Transaction 11', 'direct', 58.54, 4, 'entreprise')
            #print(transaction, flush=True)
            client_id = transaction[1]
            # Initialiser la liste si le client n'est pas déjà présent dans la map
            if client_id not in client_map:
                client_map[client_id] = [0, 0, 0, 0, 0]  # [france, particulier, entreprise, direct, nbTransaction]

            client_map[client_id][4] += 1

            # Mettre à jour les compteurs en fonction des critères
            if transaction[2] == "france":
                client_map[client_id][0] += 1
            if transaction[7] == "particulier":
                client_map[client_id][1] = 1
                client_map[client_id][2] = 0
            elif transaction[7] == "entreprise":
                client_map[client_id][1] = 0
                client_map[client_id][2] = 1
            if transaction[4] == "direct":
                client_map[client_id][3] += 1

        # Afficher la map résultante
        #for client_id, counts in client_map.items():
        #    print(f"{client_id}, {counts}")

        return client_map

    # Entraîner un modèle de décision avec scikit-learn
    def __train_decision_tree(self):
        '''
        Représentation des offres. Nous avons 4 offres pour le moment
        Donc voici la réprenstation : xxxx
        x := {0,1}
        1 pour attribution de l'offre.
        [TravelCard, TravelCardGold, VirtualCard, CarteCreditProfessionnel]
        :param transactions:
        :return:
        '''

        # data  : [france%, particulier 0 ou 1, entreprise 0 ou 1, direct%]
        # offre : [TravelCard, TravelCardGold, VirtualCard, CarteCreditProfessionnel]
        data = {
            # TravelCard
            1: [[60, 1, 0, 100], "1000"],
            2: [[53, 1, 0, 100], "1000"],
            3: [[50, 1, 0, 100], "1000"],
            4: [[40, 1, 0, 100], "1000"],

            # NO CARD
            5: [[100, 1, 0, 100], "0000"],
            6: [[83, 1, 0, 100], "0000"],
            7: [[70, 1, 0, 100], "0000"],
            8: [[61, 1, 0, 100], "0000"],

            # TravelCardGold
            9: [[39, 1, 0, 100], "0100"],
            10: [[30, 1, 0, 100], "0100"],
            11: [[17, 1, 0, 100], "0100"],
            12: [[0, 1, 0, 100], "0100"],

            # VirtualCard
            13: [[100, 1, 0, 60], "0010"],
            14: [[100, 1, 0, 50], "0010"],
            15: [[100, 1, 0, 35], "0010"],
            16: [[100, 1, 0, 0], "0010"],

            # NO VirtualCard
            17: [[100, 1, 0, 100], "0000"],
            18: [[100, 1, 0, 83], "0000"],
            19: [[100, 1, 0, 78], "0000"],
            20: [[100, 1, 0, 61], "0000"],

            # VirtualCard AND TravelCard
            21: [[60, 1, 0, 60], "1010"],
            22: [[55, 1, 0, 51], "1010"],
            23: [[50, 1, 0, 27], "1010"],
            24: [[40, 1, 0, 0], "1010"],

            # VirtualCard AND TravelCardGold
            25: [[39, 1, 0, 60], "1100"],
            26: [[22, 1, 0, 51], "1100"],
            27: [[10, 1, 0, 27], "1100"],
            28: [[0, 1, 0, 0], "1100"],

            # CarteCreditProfessionnel
            30: [[100, 0, 1, 100], "0001"],

            # TravelCard AND CarteCreditProfessionnel
            31: [[60, 0, 1, 100], "1001"],
            32: [[53, 0, 1, 100], "1001"],
            33: [[50, 0, 1, 100], "1001"],
            34: [[40, 0, 1, 100], "1001"],

            # NO TravelCard AND CarteCreditProfessionnel
            35: [[100, 0, 1, 100], "0001"],
            36: [[83, 0, 1, 100], "0001"],
            37: [[70, 0, 1, 100], "0001"],
            38: [[61, 0, 1, 100], "0001"],

            # TravelCardGold AND CarteCreditProfessionnel
            39: [[39, 0, 1, 100], "0101"],
            40: [[30, 0, 1, 100], "0101"],
            41: [[17, 0, 1, 100], "0101"],
            42: [[0, 0, 1, 100], "0101"],

            # VirtualCard AND CarteCreditProfessionnel
            43: [[100, 0, 1, 60], "0011"],
            44: [[100, 0, 1, 50], "0011"],
            45: [[100, 0, 1, 35], "0011"],
            46: [[100, 0, 1, 0], "0011"],

            # NO VirtualCard AND CarteCreditProfessionnel
            47: [[100, 0, 1, 100], "0001"],
            48: [[100, 0, 1, 83], "0001"],
            49: [[100, 0, 1, 78], "0001"],
            50: [[100, 0, 1, 61], "0001"],

            # VirtualCard AND TravelCard AND CarteCreditProfessionnel
            51: [[60, 0, 1, 60], "1011"],
            52: [[55, 0, 1, 51], "1011"],
            53: [[50, 0, 1, 27], "1011"],
            54: [[40, 0, 1, 0], "1011"],

            # VirtualCard AND TravelCardGold AND CarteCreditProfessionnel
            55: [[39, 0, 1, 60], "1101"],
            56: [[22, 0, 1, 51], "1101"],
            57: [[10, 0, 1, 27], "1101"],
            58: [[0, 0, 1, 0], "1101"],
        }

        # Organisez les données pour l'apprentissage
        X = [client_data[0] for client_data in data.values()]  # Caractéristiques du client
        y = [client_data[1] for client_data in data.values()]  # ID de l'offre


        # Entraînez le modèle
        self.model.fit(X, y)

        tree_rules = export_text(self.model, feature_names=["france%", "particulier", "entreprise", "direct%"])
        print(tree_rules)

        # Extraire les classes à partir de votre ensemble de données
        classes = [client_data[1] for client_data in data.values()]

        # Supprimer les doublons pour obtenir la liste des classes uniques
        class_names = list(set(classes))

        # Trier les classes pour maintenir un ordre cohérent
        class_names.sort()

        # Afficher la liste des class_names
        print(class_names, flush=True)

    def __predict_decision_tree(self, data):
        # Client : x : [ france, particulier, entreprise, direct ]
        prediction = self.model.predict(data)
        return prediction[0]