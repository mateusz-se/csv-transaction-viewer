package com.mat.sek.transactions.api.handler;

import com.mat.sek.transactions.api.csv.Transaction;
import com.mat.sek.transactions.api.csv.CsvParser;
import com.mat.sek.transactions.api.db.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
class TransactionCsvFileHandler implements FileHandler {

    private final CsvParser csvParser;
    private final TransactionsRepository transactionsRepository;


    @Autowired
    TransactionCsvFileHandler(CsvParser csvParser, TransactionsRepository transactionsRepository) {
        this.csvParser = csvParser;
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    public void handle(Path file) {
        List<Transaction> transactions = this.csvParser.parse(Transaction.class, file);
        transactionsRepository.removeAll();
        transactionsRepository.insert(transactions);
    }
}
