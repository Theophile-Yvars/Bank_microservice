package primabankclient.primabankclient.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
import primabankclient.primabankclient.components.TransactionService;
import primabankclient.primabankclient.controllers.dto.TransactionDTO;

@RestController
@RequestMapping(path = TransactionController.BASE_URI)
@CrossOrigin
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    public static final String BASE_URI = "/transaction";

    @Autowired
    TransactionService transactionService;

    @PostMapping("/postTransaction")
    public String postTransaction(@RequestBody TransactionDTO transactionDTO) {
        logger.info("");
        logger.info("==================================================================");
        logger.info("");
        logger.info("POST_TRANSACTION : " + transactionDTO.toString());
        return this.transactionService.doTransaction(transactionDTO);
    }

    @PostMapping("/injectTransactions/{from}/{to}")
    public String injectTransactions( @PathVariable("from") Long from, @PathVariable("to") Long to) {
        TransactionDTO transactionDTO = new TransactionDTO(
                "Test",
                from,
                52,
                "England",
                "entreprise",
                "internet",
                to
        );
        TransactionDTO transactionDTO2 = new TransactionDTO(
                "Test",
                from,
                52,
                "France",
                "particulier",
                "internet",
                to
        );
        
        for (int i = 0; i < 100; i++) {
            this.transactionService.doTransaction(transactionDTO);
        }

        for (int i = 0; i < 100; i++) {
            this.transactionService.doTransaction(transactionDTO2);
        }

        return "Injected";
    }

    @GetMapping("/getTransactions/{clientId}")
    public String getTransactions(@PathVariable("clientId") Long clientId) {
        logger.info("");
        logger.info("==================================================================");
        logger.info("");
        logger.info("GET_TRANSACTIONS : " + clientId.toString());
        return this.transactionService.getTransactions(clientId);
    }
    
}
