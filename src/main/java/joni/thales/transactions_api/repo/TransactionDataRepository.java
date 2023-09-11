package joni.thales.transactions_api.repo;

import joni.thales.transactions_api.model.Transaction;
import joni.thales.transactions_api.model.TransactionData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 *  Spring Data JPA CRUD repository for transactions.
 *
 *  @author Joni Pohjaniemi
 */
@RepositoryRestResource(exported = false)
public interface TransactionDataRepository extends CrudRepository<TransactionData, Integer> {

    List<TransactionData> findByDataKey(String key);
    List<TransactionData> findByDataValueContains(String partialString);
}
