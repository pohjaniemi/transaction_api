package joni.thales.transactions_api.service;

import joni.thales.transactions_api.model.Transaction;
import joni.thales.transactions_api.model.TransactionData;
import joni.thales.transactions_api.repo.TransactionDataRepository;
import joni.thales.transactions_api.repo.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  Service to manage key-value pairs for transactions.
 */
@Service
public class TransactionDataService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionDataService.class.getName());
    private final TransactionDataRepository transactionDataRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionDataService(TransactionDataRepository transactionDataRepository, TransactionRepository transactionRepository) {
        this.transactionDataRepository = transactionDataRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Add a key-value pair to a transaction.
     *
     * @param key   A string key
     * @param value A string value
     * @return the newly created key-value pair
     */
    public TransactionData createTransactionData(String key, String value, Integer transactionID) {
        Transaction transaction = transactionRepository.findById(transactionID)
                .orElseThrow(() -> {
                    logger.warn("Transaction ID not found: {}", transactionID);
                    return new RuntimeException("Transaction ID not found: " + transactionID);
                });

        return transactionDataRepository.save(new TransactionData(key, value, transaction));
    }

    /**
     * Search by data key.
     *
     * @return all matching key-value pairs
     */
    public Iterable<TransactionData> searchByDataKey(String key) {
        return transactionDataRepository.findByDataKey(key);
    }

    /**
     * Search by data value.
     *
     * @return all matching key-value pairs
     */
    public Iterable<TransactionData> searchByDataValue(String partialString) {
        return transactionDataRepository.findByDataValueContains(partialString);
    }

    /**
     * Calculate the number of key-value pairs in the DB.
     *
     * @return the total
     */
    public long total() { return transactionDataRepository.count(); }
}
