package joni.thales.transactions_api.service;

import joni.thales.transactions_api.model.Transaction;
import joni.thales.transactions_api.repo.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 *  Service to perform CRUD operations on authentication transactions.
 */

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class.getName());
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Create an authentication transaction.
     *
     * @param id        Unique integer ID
     * @param timestamp Transaction time
     * @param type      Type as string
     * @param actor     Actor as string
     * @return new or existing transaction
     */
    public Transaction createTransaction(Integer id, Timestamp timestamp, String type, String actor) {
        return transactionRepository.findById(id)
                .orElse(transactionRepository.save(new Transaction(id, timestamp, type, actor)));
    }

    /**
     * Find all transactions.
     *
     * @return all transactions
     */
    public Iterable<Transaction> lookup() {
        return transactionRepository.findAll();
    }

    /**
     * Search by type.
     *
     * @return all matching transactions
     */
    public Iterable<Transaction> searchByType(String type) {
        return transactionRepository.findByType(type);
    }

    /**
     * Search by actor.
     *
     * @return all matching transactions
     */
    public Iterable<Transaction> searchByActor(String actor) {
        return transactionRepository.findByActor(actor);
    }

    /**
     * Calculate the number of transactions in the DB.
     *
     * @return the total
     */
    public long total() {
        return transactionRepository.count();
    }

}
