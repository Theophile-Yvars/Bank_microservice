package al.newbank.d.PrimaBankTransaction.repositories;

import al.newbank.d.PrimaBankTransaction.entities.Transaction;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CassandraRepository<Transaction, Long> {
}
