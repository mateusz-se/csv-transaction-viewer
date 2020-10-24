package com.mat.sek.transactions.api.handler;

import com.mat.sek.transactions.api.csv.Transaction;
import com.mat.sek.transactions.api.csv.CsvParser;
import com.mat.sek.transactions.api.db.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class TransactionCsvFileHandler implements FileHandler {

    private final CsvParser csvParser;
    private final TransactionsService transactionsService;


    @Autowired
    public TransactionCsvFileHandler(CsvParser csvParser, TransactionsService transactionsService) {
        this.csvParser = csvParser;
        this.transactionsService = transactionsService;
    }

    @Override
    public void handle(Path file) {
        List<Transaction> transactions = this.csvParser.parse(Transaction.class, file);
        transactionsService.removeAll();
        transactionsService.insert(transactions);
    }
}
