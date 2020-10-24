package com.mat.sek.transactions.api.csv.parser;

import com.mat.sek.transactions.api.csv.CsvParser;
import com.mat.sek.transactions.api.csv.Price;
import com.mat.sek.transactions.api.csv.Rate;
import com.mat.sek.transactions.api.csv.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvParserTest {

    private final CsvParser csvParser = new CsvParser();

    @Test
    void testParsingTransactions() {
        Path file = Paths.get("src","test","resources", "transactions.csv");

        List<Transaction> transactions = csvParser.parse(Transaction.class, file);

        assertEquals(3, transactions.size());
        Transaction transaction = transactions.get(0);
        assertEquals(1, transaction.getId());
        assertEquals("Zakup zarowek", transaction.getTitle());
        assertEquals(new Price(BigDecimal.valueOf(123.84d), "PLN"), transaction.getPrice());
        assertEquals(LocalDate.of(2019, 5, 30), transaction.getDate());
    }

    @Test
    void testParsingRates() {
        Path file = Paths.get("src","test","resources", "rates.csv");

        List<Rate> rates = csvParser.parse(Rate.class, file);

        assertEquals(5, rates.size());
        Rate transaction = rates.get(0);
        assertEquals(BigDecimal.valueOf(4.32d), transaction.getPrice());
        assertEquals(LocalDate.of(2019, 5, 1), transaction.getFromDate());
        assertEquals(LocalDate.of(2019, 5, 12), transaction.getToDate());
    }

}