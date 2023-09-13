package joni.thales.transactions_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import joni.thales.transactions_api.dto.TransactionDataDTO;
import joni.thales.transactions_api.mapping.TransactionMapper;
import joni.thales.transactions_api.model.DataId;
import joni.thales.transactions_api.model.Transaction;
import joni.thales.transactions_api.model.TransactionData;
import joni.thales.transactions_api.service.TransactionDataService;
import joni.thales.transactions_api.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static joni.thales.transactions_api.validation.ValidationPatterns.REGEX_ALPHANUMERIC;

/**
 *  REST API for CRUD operations on key-value pairs.
 *
 *  Root endpoint: /transactions/{id}/data
 */
@RestController
@RequestMapping(path ="/transactions/{id}/data")
public class TransactionDataController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionDataController.class.getName());

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionDataService transactionDataService;

    /**
     *  List all data key-value pairs for a transaction.
     *
     *  Optional search query parameter.
     *  /transactions/{id}/data?search=
     *
     *  @param id Transaction id
     *  @param search String to search for in values (optional)
     *
     *  @return list of key-value pairs
     *  @throws NoSuchElementException if transaction was not found
     */
    @Operation(summary = "List all key-value pairs (or search)", description = "List all data key-value pairs for a transaction. You can also search for strings in values.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The transaction was not found")
    })
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<TransactionDataDTO> listTransactionData(@PathVariable("id") Integer id,
                                                        @RequestParam(value = "search", required = false) String search) {
        logger.debug("GET /transactions/{}/data", id);
        logger.info("Search: {}", search);

        Optional<Transaction> transactionOptional = transactionService.findById(id);
        if (transactionOptional.isPresent()){
            Transaction transaction = transactionOptional.get();
            // List all
            if (search == null){
                List<TransactionData> dataList = transaction.getData();
                if (dataList != null){
                    return dataList.stream().map(TransactionMapper::convertDataToDTO).collect(Collectors.toList());
                }
            }
            // Search in values
            else {
                return transactionDataService.searchByValue(transaction, search).stream()
                        .map(TransactionMapper::convertDataToDTO).collect(Collectors.toList());
            }

        }
        else {
            throw new NoSuchElementException("Transaction not found.");
        }

        return null;
    }

    /**
     *  Create (or update) a key-value pair.
     */
    @Operation(summary = "Save key-value pair", description = "Create or update an existing key-value pair.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "404", description = "Not found - The transaction was not found")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTransactionData(@PathVariable("id") Integer id,
                                      @RequestBody TransactionDataDTO transactionDataDTO) {
        final String dataKey = transactionDataDTO.getDataKey();
        logger.debug("POST /transactions/{}/data  -> Data key {}", id, dataKey);

        Optional<Transaction> transactionOptional = transactionService.findById(id);
        if (transactionOptional.isPresent()) {
            Transaction transaction = transactionOptional.get();
            transactionDataService.save(TransactionMapper.convertDataToEntity(transactionDataDTO, transaction));
        }
    }

    /**
     *  Get a data key-value pair.
     *
     *  @return transaction
     *  @throws NoSuchElementException if transaction was not found
     */
    @Operation(summary = "Get key-value pair", description = "Find data key-value pair by composite data ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - Either the transaction or the data key was not found")
    })
    @GetMapping("/{dataKey:"+REGEX_ALPHANUMERIC+"}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TransactionDataDTO getTransactionData(@PathVariable("id") Integer id,
                                                 @PathVariable("dataKey") String dataKey) {
        logger.debug("GET /transactions/{}/data/{}", id, dataKey);

        Optional<Transaction> transactionOptional = transactionService.findById(id);
        if (transactionOptional.isPresent()) {
            Transaction transaction = transactionOptional.get();
            Optional<TransactionData> transactionDataOptional = transactionDataService.findById(
                    new DataId(transaction, dataKey));
            if (transactionDataOptional.isPresent()) {
                TransactionData transactionData = transactionDataOptional.get();
                return TransactionMapper.convertDataToDTO(transactionData);
            } else {
                throw new NoSuchElementException("Data key not found.");
            }
        }
        throw new NoSuchElementException("Transaction not found.");
    }

    /**
     *  Delete a specific key-value pair.
     *
     *  @throws NoSuchElementException if either transaction or the data key was not found
     */
    @Operation(summary = "Delete key-value pair", description = "Delete a specific data key-value pair by composite data ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - Either the transaction or the data key was not found")
    })
    @DeleteMapping("/{dataKey:"+REGEX_ALPHANUMERIC+"}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void deleteTransactionData(@PathVariable("id") Integer id,
                                      @PathVariable("dataKey") String dataKey) {
        logger.debug("DELETE /transactions/{}/data/{}", id, dataKey);

        Optional<Transaction> transactionOptional = transactionService.findById(id);
        if (transactionOptional.isPresent()) {
            Transaction transaction = transactionOptional.get();
            DataId dataId = new DataId(transaction, dataKey);
            transactionDataService.findById(dataId).ifPresentOrElse(
                    (transactionData) -> { transactionDataService.deleteById(dataId); },
                    () -> { throw new NoSuchElementException("Deletion failed. Key-value pair not found."); }
            );
        }
        throw new NoSuchElementException("Transaction not found.");
    }

    /**
     * Exception handler if NoSuchElementException is thrown.
     *
     * @param ex exception
     * @return Error message
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        logger.error("Item not found", ex);
        return ex.getMessage();
    }
}
