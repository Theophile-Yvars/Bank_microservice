package al.newbank.d.Saver.repositories;
import al.newbank.d.Saver.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT DISTINCT t.client FROM Transaction t")
    List<Long> findAllDistinctAccounts();

    @Query("SELECT t FROM Transaction t WHERE t.client = :accountId")
    List<Transaction> findTransactionsByAccount(@Param("accountId") Long accountId);

    @Query("SELECT * FROM Transaction t ")
    List<Transaction> findByCreatedAtBefore(LocalDateTime date);
}
