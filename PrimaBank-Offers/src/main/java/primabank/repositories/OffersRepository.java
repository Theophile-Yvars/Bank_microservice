package primabank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import primabank.entities.Offer;

import java.util.List;

@Repository
public interface OffersRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByIdClient(Long idClient);
}
