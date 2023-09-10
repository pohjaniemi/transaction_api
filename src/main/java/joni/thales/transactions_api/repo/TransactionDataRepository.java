package joni.thales.transactions_api.repo;

import joni.thales.transactions_api.model.TransactionData;
import org.springframework.data.repository.CrudRepository;

/**
 *  Spring Data JPA CRUD repository for transactions.
 *
 *  Created by Joni Pohjaniemi
 */
public interface TransactionDataRepository extends CrudRepository<TransactionData, Integer> {
}
