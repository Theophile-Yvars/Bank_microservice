package primabankclient.primabankclient.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import primabankclient.primabankclient.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {



}
