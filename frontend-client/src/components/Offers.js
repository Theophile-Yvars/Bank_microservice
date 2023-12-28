import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './styles/offers.css';

const Offers = ({ idClient }) => {
    const [offers, setOffers] = useState([]);

    useEffect(() => {
        console.log("ID Client : ", idClient);
        // Effectuer une requête à votre API Spring pour récupérer la liste d'offres
        axios.get(`http://localhost:8088/clients/clients/showProduct/${idClient}`)
            .then(response => {
                setOffers(response.data);
            })
            .catch(error => {
                console.error('Erreur lors de la récupération des offres:', error);
            });
    }, [idClient]);

    console.log("Chargement des offres");

    return (
        <div>
            <h1 className="offers-title">Client ID : {idClient}</h1>
            <div className="offers">
                {offers.map((offer) => (
                    <div key={offer.idClient} className="offer-item">
                        <div className="offer-container">
                            <div className="image-container">
                                {/* Utilisation d'une structure conditionnelle pour déterminer quelle image afficher */}
                                {offer.productName === "TravelCard" && (
                                    <img src="https://www.cartedecredit.fr/images/product/bunq-travel-card-prepaid-credit-card(1).png" alt="Description de l'image" />
                                )}
                                {offer.productName === "Carte Crédit Professionnel" && (
                                    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9JvcqlnA-1WlBfeAjopzNc2-mSXL2BxRqEA&usqp=CAU" alt="Description de l'image" />
                                )}
                                {offer.productName === "TravelCardGold" && (
                                    <img src="https://suitespot.fr/wp-content/uploads/2020/02/Carte-Gold-Mastercard-ING.jpg" alt="Description de l'image" />
                                )}
                                {offer.productName === "Virtual Card" && (
                                    <img src="https://www.manager.one/_nuxt/img/masterhead_virtual-card.3161b26.png" alt="Description de l'image" />
                                )}
                                {/* Ajoutez d'autres conditions en fonction des valeurs possibles de productName */}

                                {/* Condition de fin par défaut si aucune condition n'est remplie */}
                                {!["TravelCard", "Carte Crédit Professionnel", "TravelCardGold", "Virtual Card"].includes(offer.productName) && (
                                    <img src="https://banque.meilleurtaux.com/images/guide-banque/conseils-carte-bancaire.jpg" alt="Image par défaut" />
                                )}
                            </div>
                            <div className="text-container">
                                <h1 className="text-title">{offer.productName}</h1>
                                <p><b>Description :</b> {offer.productDescription}</p>
                                <p><b>Prix :</b> {offer.productPrice} € par mois</p>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Offers;