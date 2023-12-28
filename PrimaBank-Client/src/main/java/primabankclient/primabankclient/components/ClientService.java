package primabankclient.primabankclient.components;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import primabankclient.primabankclient.entities.Account;
import primabankclient.primabankclient.entities.Client;
import primabankclient.primabankclient.repositories.AccountRepository;
import primabankclient.primabankclient.repositories.ClientRepository;

@Component
@Transactional
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    public Client  registerClient(String name) {
        Client client = new Client(name);
        Account account = new Account(client.getId(), client.getName());
        Account createdAccount =this.accountRepository.save(account);
        client.setAccountId(createdAccount.getId());
        Client createdClient = this.clientRepository.save(client);
        account.setClientId(createdClient.getAccountId());
        this.accountRepository.save(account);
        return createdClient;
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public void createClientAccount(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client != null) {
            Account account = new Account(client.getId(), client.getName());
            this.accountRepository.save(account);

        }
    }

    public Account getClientAccount(Long id) {
        return this.accountRepository.findByClientId(id);
    }

    public Account rechargeAccount(Long id, double amount) {
        Account account = this.accountRepository.findByClientId(id);
        account.setAmount(account.getAmount() + amount);
        return this.accountRepository.save(account);
    }

    public Account debitAccount(Long id, double amount) {
        Account account = this.accountRepository.findByClientId(id);
        account.setAmount(account.getAmount() - amount);
        return this.accountRepository.save(account);
    }

    public double consultMyAccount(Long id) {
        Account account = this.accountRepository.findByClientId(id);
        return account.getAmount();
    }

    public List<Account> getAllAccounts() {
        return this.accountRepository.findAll();
    }

    




}
