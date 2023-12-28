package primabankclient.primabankclient.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import primabankclient.primabankclient.components.ClientService;
import primabankclient.primabankclient.components.ShowProduct;
import primabankclient.primabankclient.controllers.dto.PaymentDto;
import primabankclient.primabankclient.controllers.dto.ProductDto;
import primabankclient.primabankclient.dto.AccountDto;
import primabankclient.primabankclient.entities.Account;
import primabankclient.primabankclient.entities.Client;
import primabankclient.primabankclient.repositories.ClientRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = ClientController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class ClientController {
    private Logger logger = LoggerFactory.getLogger(ClientController.class);


    public static final  String BASE_URI = "/clients";

    public ClientRepository clientRepository;
    public ShowProduct showProduct;
    public ClientService clientService;

    public ClientController(ClientRepository clientRepository, ShowProduct showProduct,ClientService clientService) {
        this.clientRepository = clientRepository;
        this.showProduct = showProduct;
        this.clientService = clientService;

    }

    @GetMapping
    public List<Client> getClients() {
        logger.info("");
        logger.info("==================================================================");
        logger.info("");
        logger.info("GET CLIENTS");
        List<Client> clients = this.clientRepository.findAll();
        logger.info("List of clients : " + clients);
        return clients;
    }

    @PostMapping("/register/{name}")
    public Client createClient( @PathVariable String name) {
        logger.info("");
        logger.info("==================================================================");
        logger.info("");
        logger.info("CREATE CLIENT");
        return this.clientService.registerClient(name);
    }

    @PostMapping("/payment")
    public void payment(@RequestBody PaymentDto paymentDto) {
        System.out.println(paymentDto);
        //return this.clientRepository.save(client);
    }

    @GetMapping("/showProduct/{clientId}")
    public ResponseEntity<List<ProductDto>> showProduct(@PathVariable Long clientId) {
        logger.info("");
        logger.info("==================================================================");
        logger.info("");
        logger.info("SHOW PRODUCT : " + clientId);
        ResponseEntity<List<ProductDto>> products = ResponseEntity.ok(this.showProduct.getAdvertisementForClient(clientId));
        logger.info("Products : " + products.getBody());
        return products;
    }

    @GetMapping("/recharge/{clientId}/{amount}")
    public ResponseEntity<Account> rechargeAccount(@PathVariable Long clientId, @PathVariable double amount) {
        Long accountId  = this.clientRepository.findById(clientId).orElse(null).getAccountId();
        return ResponseEntity.ok(this.clientService.rechargeAccount(accountId, amount));
    }

    @GetMapping("/getAllAccounts")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        logger.info("");
        logger.info("==================================================================");
        logger.info("");
        logger.info("GET ALL ACCOUNTS");
       List<Account> accounts = this.clientService.getAllAccounts();
       List<AccountDto> accountDtos = new ArrayList<>();
       for (int i = 0; i < accounts.size(); i++) {
              accountDtos.add(new AccountDto(accounts.get(i).getClientId(), accounts.get(i).getClientName(),accounts.get(i).getAmount(),accounts.get(i).getCreditCardNumber()));
        }
       logger.info("Accounts : " + accountDtos);
        return ResponseEntity.ok(accountDtos);
    }
}
