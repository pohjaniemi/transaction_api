package joni.thales.transactions_api.service;

import joni.thales.transactions_api.model.Transaction;
import joni.thales.transactions_api.model.TransactionData;
import joni.thales.transactions_api.model.DataId;
import joni.thales.transactions_api.repository.TransactionDataRepository;
import joni.thales.transactions_api.repository.TransactionRepository;
import org.apache.commons.collections4.IterableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
     * @param transactionID Transaction ID
     * @return the newly created key-value pair
     */
    public TransactionData createTransactionData(String key, String value, Integer transactionID) {
        final Transaction transaction = transactionRepository.findById(transactionID)
                .orElseThrow(() -> {
                    logger.warn("Transaction ID not found: {}", transactionID);
                    return new NoSuchElementException("Transaction ID not found: " + transactionID);
                });

        final DataId dataId = new DataId(transaction, key);
        return transactionDataRepository.save(new TransactionData(dataId, value));
    }

    /**
     * Save a key-value pair to a transaction.
     *
     * @param transactionData Transaction data object to save
     */
    public void save(TransactionData transactionData) {
        logger.info("Saving key-value pair with data key {}", transactionData.getId().getDataKey());
        transactionDataRepository.save(transactionData);
    }

    /**
     * Find data key-value pair by composite data ID.
     *
     * @param id Composite data ID
     * @return optional with key-value pair
     */
    public Optional<TransactionData> findById(DataId id) {
        return transactionDataRepository.findById(id);
    }

    /**
     * Delete data key-value pair by composite data ID.
     *
     * @param id Composite data ID
     */
    public void deleteById(DataId id) {
        transactionDataRepository.deleteById(id);
    }

    /**
     * Search by data value.
     *
     * @param transaction Transaction scope
     * @param partialString String to search for in values
     * @return all matching key-value pairs
     */
    public List<TransactionData> searchByValue(Transaction transaction,
                                               String partialString) {
        return IterableUtils.toList(
                transactionDataRepository.findById_TransactionAndDataValueContains(transaction, partialString)
        );
    }

}
