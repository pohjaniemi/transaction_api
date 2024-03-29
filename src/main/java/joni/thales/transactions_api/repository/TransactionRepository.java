package joni.thales.transactions_api.repository;

import joni.thales.transactions_api.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 *  Spring CRUD repository for transactions.
 *  Extended with custom search operations.
 */
@RepositoryRestResource(exported = false)
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

    List<Transaction> findByType(String type);
    List<Transaction> findByActor(String actor);
    List<Transaction> findByTypeAndActor(String type, String actor);

}
