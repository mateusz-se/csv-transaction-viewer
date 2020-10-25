package com.mat.sek.transactions.api.handler;

import com.mat.sek.transactions.api.csv.CsvParser;
import com.mat.sek.transactions.api.csv.Transaction;
import com.mat.sek.transactions.api.db.TransactionsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionCsvFileHandlerTest {


    @Mock
    private CsvParser csvParser;

    @Mock
    private TransactionsRepository transactionsRepository;

    @InjectMocks
    private TransactionCsvFileHandler transactionCsvFileHandler;

    @Test
    void handle() {
        Path pathToCsvFile = Path.of("src", "test");
        List<Transaction> transactions = List.of(createTransaction(1, "test"),
                createTransaction(2, "test 2"));
        when(csvParser.parse(Transaction.class, pathToCsvFile)).thenReturn(
                transactions
        );
        transactionCsvFileHandler.handle(pathToCsvFile);
        verify(transactionsRepository, times(1)).removeAll();
        verify(transactionsRepository, times(1)).insert(transactions);
    }

    private Transaction createTransaction(int id, String title) {
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setTitle(title);
        return transaction;
    }
}