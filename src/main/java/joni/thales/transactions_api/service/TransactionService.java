package joni.thales.transactions_api.service;

import joni.thales.transactions_api.model.Transaction;
import joni.thales.transactions_api.repository.TransactionRepository;
import org.apache.commons.collections4.IterableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

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
    public Transaction create(Integer id, Timestamp timestamp, String type, String actor) {
        logger.info("Create transaction with ID {}", id);
        return transactionRepository.findById(id)
                .orElse(transactionRepository.save(new Transaction(id, timestamp, type, actor)));
    }

    /**
     * Save an authentication transaction object.
     *
     * @param transaction Transaction object to save
     */
    public void save(Transaction transaction) {
        logger.info("Saving transaction with ID {}", transaction.getId());
        transactionRepository.save(transaction);
    }

    /**
     * Find all transactions.
     *
     * @return all transactions
     */
    public List<Transaction> findAll() {
        return IterableUtils.toList(transactionRepository.findAll());
    }

    /**
     * Find transaction by ID.
     *
     * @param id Transaction ID
     * @return optional with transaction
     */
    public Optional<Transaction> findById(Integer id) {
        return transactionRepository.findById(id);
    }

    /**
     * Delete transaction by ID.
     *
     * @param id Transaction ID
     */
    public void deleteById(Integer id) {
        transactionRepository.deleteById(id);
    }

    /**
     * Search by type.
     *
     * @return all matching transactions
     */
    public List<Transaction> searchByType(String type) {
        return transactionRepository.findByType(type);
    }

    /**
     * Search by actor.
     *
     * @return all matching transactions
     */
    public List<Transaction> searchByActor(String actor) {
        return transactionRepository.findByActor(actor);
    }

    /**
     * Search by type and actor.
     *
     * @return all matching transactions
     */
    public List<Transaction> searchByTypeAndActor(String type, String actor) {
        return transactionRepository.findByTypeAndActor(type, actor);
    }
}
