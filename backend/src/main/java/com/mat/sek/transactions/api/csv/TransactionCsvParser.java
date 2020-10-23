package com.mat.sek.transactions.api.csv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class TransactionCsvParser implements Parser {

    private final CsvParser csvParser;

    @Autowired
    public TransactionCsvParser(CsvParser csvParser) {
        this.csvParser = csvParser;
    }

    @Override
    public void parse(Path file) {
        List<Transaction> parse = this.csvParser.parse(Transaction.class, file);
        System.out.println(parse);
    }
}
