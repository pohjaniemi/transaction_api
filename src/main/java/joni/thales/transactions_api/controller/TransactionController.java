package joni.thales.transactions_api.controller;

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

    @GetMapping
    @ResponseBody
    public List<TransactionDTO> listTransactions() {
        logger.debug("GET /transactions");
        return transactionService.lookup().stream().map(TransactionMapper::convertToDto).collect(Collectors.toList());
    }

    @PostMapping
    public void createTransaction(@RequestBody TransactionDTO transactionDTO) {
        logger.debug("POST /transactions/  -> Transaction id {}", transactionDTO.getId());
        transactionService.save(TransactionMapper.convertToEntity(transactionDTO));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public TransactionDTO getTransaction(@PathVariable("id") Integer id) {
        logger.debug("GET /transactions/{}", id);
        return transactionService.findById(id)
                .map(TransactionMapper::convertToDto)
                .orElseThrow(() -> new NoSuchElementException("Item not found."));
    }

    @DeleteMapping("/{id}")
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
     * @return Error message String.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        logger.error("Item not found", ex);
        return ex.getMessage();
    }
}
