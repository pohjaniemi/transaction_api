package joni.thales.transactions_api.service;

import joni.thales.transactions_api.mapping.TransactionMapper;
import joni.thales.transactions_api.model.Transaction;
import joni.thales.transactions_api.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionServiceTest {

    private static final Integer TRANSACTION_ID = 123;
    private static final Integer TRANSACTION_ID_NEW = 1234;
    private static final String TRANSACTION_TIMESTAMP = "2023-12-11 10:09:08";
    private static final String TRANSACTION_TYPE = "Testing";
    private static final String TRANSACTION_ACTOR = "Client Name";

    @Mock
    private TransactionRepository transactionRepositoryMock;
    @Mock
    private Transaction transactionMock;
    @Mock
    private Transaction transactionMock2;

    @InjectMocks
    private TransactionService service;

    @Test
    public void save() {
        final Timestamp timestamp = TransactionMapper.toSQLTimestamp(TRANSACTION_TIMESTAMP);
        final Transaction transaction = new Transaction(TRANSACTION_ID_NEW, timestamp, TRANSACTION_TYPE, TRANSACTION_ACTOR);

        service.save(transaction);

        verify(transactionRepositoryMock).save(transaction);
    }

    @Test
    public void findAll() {
        when(transactionRepositoryMock.findAll())
                .thenReturn(Arrays.asList(transactionMock, transactionMock2));

        List<Transaction> transactionList = service.findAll();

        assertEquals(transactionList.size(), 2);
        assertEquals(transactionList.get(0), transactionMock);
        assertEquals(transactionList.get(1), transactionMock2);
    }

    @Test
    public void findById() {
        when(transactionRepositoryMock.findById(TRANSACTION_ID))
                .thenReturn(Optional.of(transactionMock));

        assertEquals(service.findById(TRANSACTION_ID).get(), transactionMock);
    }

    @Test
    public void deleteById() {
        service.deleteById(TRANSACTION_ID);

        verify(transactionRepositoryMock).deleteById(TRANSACTION_ID);
    }

}
