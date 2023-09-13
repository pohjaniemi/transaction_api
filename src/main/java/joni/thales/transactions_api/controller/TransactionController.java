package joni.thales.transactions_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import joni.thales.transactions_api.dto.TransactionDTO;
import joni.thales.transactions_api.mapping.TransactionMapper;
import joni.thales.transactions_api.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 *  REST API for CRUD operations on authentication transactions.
 *
 *  Root endpoint: /transactions
 */
@RestController
@RequestMapping(path ="/transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class.getName());

    @Autowired
    private TransactionService transactionService;

    /**
     *  List all transactions.
     *
     *  @return list of transactions
     */
    @Operation(summary = "List all transactions", description = "Get list of all transactions.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<TransactionDTO> listTransactions() {
        logger.debug("GET /transactions");
        return transactionService.lookup().stream().map(TransactionMapper::convertToDto).collect(Collectors.toList());
    }

    /**
     *  Create (or update) a transaction.
     */
    @Operation(summary = "Save transaction", description = "Create or update an existing transaction.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "404", description = "Not found - The transaction was not found")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTransaction(@RequestBody TransactionDTO transactionDTO) {
        logger.debug("POST /transactions/  -> Transaction id {}", transactionDTO.getId());
        transactionService.save(TransactionMapper.convertToEntity(transactionDTO));
    }

    /**
     *  Get a specific transaction by ID.
     *
     *  @return transaction
     *  @throws NoSuchElementException if transaction was not found
     */
    @Operation(summary = "Get transaction by ID", description = "Get a specific transaction by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The transaction was not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TransactionDTO getTransaction(@PathVariable("id") Integer id) {
        logger.debug("GET /transactions/{}", id);
        return transactionService.findById(id)
                .map(TransactionMapper::convertToDto)
                .orElseThrow(() -> new NoSuchElementException("Item not found."));
    }

    /**
     *  Delete a specific transaction by ID.
     *
     *  @throws NoSuchElementException if transaction was not found
     */
    @Operation(summary = "Delete transaction by ID", description = "Delete a specific transaction by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Not found - The transaction was not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransaction(@PathVariable("id") Integer id) {
        logger.debug("DELETE /transactions/{}", id);
        transactionService.findById(id).ifPresentOrElse(
                (transaction) -> { transactionService.deleteById(transaction.getId()); },
                () -> { throw new NoSuchElementException("Deletion failed. Item not found."); }
        );
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
