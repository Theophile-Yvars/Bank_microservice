package primabankclient.primabankclient.components;
import primabankclient.primabankclient.entities.Client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jakarta.transaction.Transactional;
import primabankclient.primabankclient.controllers.TransactionController;
import primabankclient.primabankclient.controllers.dto.TransactionDTO;

@Component
@Transactional
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);


    @Autowired
    public ClientService clientService;

    public String transactionUrl = "http://service-transaction-backend:8080/transaction/postTransaction";

    public RestTemplate restTemplate = new RestTemplate();
    
    public String doTransaction(TransactionDTO transactionDTO){
        Long from = transactionDTO.getClient();
        Long to = transactionDTO.getTargetClient();
        String reponse = restTemplate.postForObject(transactionUrl, transactionDTO, String.class);
        // todo: debit account
//        Client fromClient = this.clientService.getClientById(from);
//        Client toClient = this.clientService.getClientById(to);
//        if (reponse!=null && fromClient!=null && toClient!=null){
//            this.clientService.debitAccount(from, transactionDTO.getPrice());
//            this.clientService.rechargeAccount(to, transactionDTO.getPrice());
//            return reponse;
//        }
        return "Transaction success";
    }

    public String getTransactions(Long clientId){
        Client client = this.clientService.getClientById(clientId);
        if (client!=null){
            String url = "http://service-transaction-backend:8080/transaction/getAllTransactions/"+client.getId();
            String reponse = restTemplate.getForObject(url, String.class);
            logger.info("GET_TRANSACTIONS_OF_CLIENT_"+clientId+" : " +"\n" + reponse);
            return reponse;
        }
        return "Client not found";
    }
}
