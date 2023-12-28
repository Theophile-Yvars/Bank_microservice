package primabankclient.primabankclient.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import primabankclient.primabankclient.entities.Account;

public interface AccountRepository  extends JpaRepository<Account, Long>{

    Account findByClientId(Long id);
    
}
