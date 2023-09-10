package joni.thales.transactions_api.repo;

import joni.thales.transactions_api.model.Transaction;
import org.springframework.data.repository.CrudRepository;

/**
 *  Spring Data JPA CRUD repository for transactions.
 *
 *  Created by Joni Pohjaniemi
 */
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
}
