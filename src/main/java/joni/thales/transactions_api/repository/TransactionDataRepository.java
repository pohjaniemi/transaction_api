package joni.thales.transactions_api.repository;

import joni.thales.transactions_api.model.TransactionData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 *  Spring CRUD repository for key-value pairs.
 */
@RepositoryRestResource(exported = false)
public interface TransactionDataRepository extends CrudRepository<TransactionData, Integer> {

    List<TransactionData> findByIdDataKey(String key);
    List<TransactionData> findByDataValueContains(String partialString);
}
