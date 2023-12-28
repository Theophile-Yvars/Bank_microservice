package al.newbank.d.Marketing.components;

import al.newbank.d.Marketing.dto.AnalysisDTO;
import al.newbank.d.Marketing.dto.ProductDTO;
import al.newbank.d.Marketing.dto.TransactionDTO;
import al.newbank.d.Marketing.entities.Transaction;
import al.newbank.d.Marketing.interfaces.IBanker;
import al.newbank.d.Marketing.interfaces.IOffers;
import al.newbank.d.Marketing.interfaces.IProduceAnalyse;
import al.newbank.d.Marketing.interfaces.IProduceEvent;
import al.newbank.d.Marketing.repositories.TransactionRepository;
import org.apache.tomcat.util.net.IPv6Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Analyzer implements IProduceAnalyse {
    private Logger logger = LoggerFactory.getLogger(Analyzer.class);


    private IProduceEvent produceEvent;
    private IBanker banker;
    private TransactionRepository transactionRepository;
    private SeasonDetecter seasonDetecter;


    private List<TransactionDTO> transactions = new ArrayList<>();
    private List<ProductDTO> products = new ArrayList();

    private IOffers offers;

    @Autowired
    public Analyzer(IProduceEvent produceEvent, IBanker banker, TransactionRepository transactionRepository,SeasonDetecter seasonDetecter, IOffers offers) {
        this.produceEvent = produceEvent;
        this.banker = banker;
        this.transactionRepository = transactionRepository;
        this.seasonDetecter = seasonDetecter;
        this.offers = offers;
    }

    @Override
    public void startAnalysis() {
        logger.info("DELETE OFFERS");
        boolean rep = this.offers.delete();

        if(rep) {
            logger.info("New Analysis is starting");
            this.products = this.banker.getAllProducts();

            Map<Long, List<TransactionDTO>> clientTransactions = createClientTransactions(); // Client + list de transaction

            for (Map.Entry<Long, List<TransactionDTO>> entry : clientTransactions.entrySet()) {
                Long client = entry.getKey();
                List<TransactionDTO> transactions = entry.getValue();
                logger.info("Client: " + client);

                analyzeClientTransactions(client, transactions);
            }
        }else{
            logger.error("ERROR DELETE ...");
        }
    }

    public String retrieveSeason(){
        return this.seasonDetecter.isInSeasonalPeriod(LocalDate.now());    
    }

    private Map<Long, List<TransactionDTO>> createClientTransactions() {
        Map<Long, List<TransactionDTO>> clientTransactions = new HashMap<>();

        List<Long> accounts = this.transactionRepository.findAllDistinctAccounts();

        logger.info("Account list : ", accounts);

        for(Long account : accounts){
            List<Transaction> transactionList = this.transactionRepository.findTransactionsByAccount(account);
            List<TransactionDTO> transactionDtoList = this.transactionToDTO(transactionList);
            logger.info("Account : ", account);
            logger.info("Transactions : ", transactionDtoList);
            clientTransactions.put(account, transactionDtoList);
        }

        return clientTransactions;
    }

    private List<TransactionDTO> transactionToDTO(List<Transaction> transactionList){
        List<TransactionDTO> transactionDTOList = new ArrayList<>();

        for(Transaction transaction : transactionList){
            TransactionDTO transactionDTO = new TransactionDTO(
                    transaction.getName(),
                    transaction.getClient(),
                    transaction.getPrice(),
                    transaction.getCountry(),
                    transaction.getType(),
                    transaction.getOrigine(),
                    transaction.getTagetClient()
            );

            transactionDTOList.add(transactionDTO);
        }
        return transactionDTOList;
    }

    private void analyzeClientTransactions(Long client, List<TransactionDTO> transactions) {
        int numberOfTransactionInForeignCountry = 0;
        int numberOfInternetTransaction = 0;
        int numberOfEnterpriseTransaction = 0;

        for(TransactionDTO transaction : transactions) {
            //logger.info("Country : " + transaction.getCountry());
            if (!"france".equals(transaction.getCountry())) {
                numberOfTransactionInForeignCountry++;
            }
            if ("internet".equals(transaction.getOrigine())) {
                numberOfInternetTransaction++;
            }
            if ("entreprise".equals(transaction.getType())) {
                numberOfEnterpriseTransaction++;
            }
        }

        double percentageOfForeignTransaction = (numberOfTransactionInForeignCountry * 100.0) / transactions.size();
        double percentageOfInternetTransaction = (numberOfInternetTransaction * 100.0) / transactions.size();
        double percentageOfEnterpriseTransaction = (numberOfEnterpriseTransaction * 100.0) / transactions.size();

        logger.info("percentage of foreign transaction : " + percentageOfForeignTransaction + "%");
        logger.info("percentage Of Internet Transaction : " + percentageOfInternetTransaction + "%");
        logger.info("percentage Of Enterprise Transaction : " + percentageOfEnterpriseTransaction + "%");

        logger.info("number of transaction in foreign country : " + numberOfTransactionInForeignCountry);
        logger.info("number of internet transaction : " + numberOfInternetTransaction);
        logger.info("number of enterprise transaction : " + numberOfEnterpriseTransaction);


        if (percentageOfEnterpriseTransaction >= 50.0) {
            logger.info("PROFESSIONAL CARD");
            sendAnalysis(client, products.get(3));
        }

        if (percentageOfInternetTransaction >= 20.0) {
            logger.info("VIRTUAL CARD");
            sendAnalysis(client, products.get(2));
        }

        if (percentageOfForeignTransaction >= 40.0) {
            logger.info("TRAVEL CARD GOLD");
            sendAnalysis(client, products.get(1));
        }
        else if (percentageOfForeignTransaction >= 20.0) {
            logger.info("TRAVEL CARD");
            sendAnalysis(client, products.get(0));
        }
    }

    private void sendAnalysis(Long client, ProductDTO product) {
        AnalysisDTO analysis = new AnalysisDTO(client, product);
        this.produceEvent.send(analysis);
    }
}
