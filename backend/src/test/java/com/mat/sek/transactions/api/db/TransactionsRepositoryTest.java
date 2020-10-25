package com.mat.sek.transactions.api.db;

import com.mat.sek.transactions.api.SearchParams;
import com.mat.sek.transactions.api.SpringBootApplication;
import com.mat.sek.transactions.api.csv.Price;
import com.mat.sek.transactions.api.csv.Rate;
import com.mat.sek.transactions.api.csv.Transaction;
import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.mat.sek.transactions.api.db.model.public_.tables.Rates.RATES;
import static com.mat.sek.transactions.api.db.model.public_.tables.Transactions.TRANSACTIONS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApplication.class)
class TransactionsRepositoryTest {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private DSLContext dsl;

    @BeforeEach
    void setUp() {
        dsl.deleteFrom(TRANSACTIONS)
                .execute();
        dsl.deleteFrom(RATES)
                .execute();
    }

    @Test
    void insertTransactionsIntoDB() {
        List<Transaction> transactionsToInsert = List.of(
                getTransaction(1, BigDecimal.TEN, "Test", LocalDate.of(2020, 10, 12)),
                getTransaction(2, BigDecimal.valueOf(4d), "Test 2", LocalDate.of(2020, 10, 14))
        );

        transactionsRepository.insert(transactionsToInsert);

        List<Transaction> fetchedTransactions = dsl.select()
                .from(TRANSACTIONS)
                .fetch()
                .stream()
                .map(r ->
                        getTransaction(
                                r.get(TRANSACTIONS.ID),
                                r.get(TRANSACTIONS.PRICE),
                                r.get(TRANSACTIONS.TITLE),
                                r.get(TRANSACTIONS.TRANSACTION_DATE))
                ).collect(Collectors.toList());

        assertEquals(transactionsToInsert, fetchedTransactions);
    }

    @Test
    void removeAllTransactionsFromDB() {
        Transaction toInsert = getTransaction(1, BigDecimal.valueOf(4d), "Test 2", LocalDate.of(2020, 10, 14));
        insertTransaction(toInsert);

        transactionsRepository.removeAll();

        long transactionsCount = dsl.select()
                .from(TRANSACTIONS)
                .fetch()
                .size();
        assertEquals(0, transactionsCount);
    }

    @Test
    void getSecondTransactionFromDBWithoutPriceInEur() {
        Transaction toInsert = getTransaction(1, BigDecimal.valueOf(4d), "Test 1", LocalDate.of(2020, 10, 14));
        insertTransaction(toInsert);
        Transaction toInsert2 = getTransaction(2, BigDecimal.valueOf(10.21d), "Test 2", LocalDate.of(2020, 9, 14));
        insertTransaction(toInsert2);

        List<TransactionDTO> transactions = transactionsRepository.getTransactions(
                SearchParams.builder()
                        .results(1)
                        .page(2)
                        .build()
        );

        assertEquals(1, transactions.size());
        TransactionDTO transactionDTO = transactions.get(0);
        assertNull(transactionDTO.getPriceEur());
        assertEquals(BigDecimal.valueOf(10.21d), transactionDTO.getPricePln());
        assertEquals(2, transactionDTO.getId());
    }

    @Test
    void getNewestTransactionFromDBWithPriceInEur() {
        BigDecimal plnPrice = BigDecimal.valueOf(10.21d);
        BigDecimal eurPrice = BigDecimal.valueOf(4.32d);
        insertTransaction(getTransaction(1, BigDecimal.valueOf(4d), "Test 1", LocalDate.of(2020, 10, 14)));
        insertTransaction(getTransaction(2, plnPrice, "Test 2", LocalDate.of(2021, 9, 14)));
        insertRate(getRate(eurPrice));

        List<TransactionDTO> transactions = transactionsRepository.getTransactions(
                SearchParams.builder()
                        .results(1)
                        .page(1)
                        .build()
        );

        assertEquals(1, transactions.size());
        TransactionDTO transactionDTO = transactions.get(0);
        assertEquals(plnPrice.divide(eurPrice, new MathContext(3, RoundingMode.HALF_UP)), transactionDTO.getPriceEur());
        assertEquals(BigDecimal.valueOf(10.21d), transactionDTO.getPricePln());
        assertEquals(2, transactionDTO.getId());
    }

    private void insertTransaction(Transaction toInsert) {
        dsl.insertInto(TRANSACTIONS,
                TRANSACTIONS.ID,
                TRANSACTIONS.TITLE,
                TRANSACTIONS.TRANSACTION_DATE,
                TRANSACTIONS.PRICE,
                TRANSACTIONS.CURRENCY
        ).values(toInsert.getId(),
                 toInsert.getTitle(),
                 toInsert.getDate(),
                 toInsert.getPrice().getAmount(),
                 toInsert.getPrice().getCurrency())
                .execute();
    }

    private Transaction getTransaction(Integer id, BigDecimal price, String title, LocalDate date) {
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setPrice(new Price(price, "PLN"));
        transaction.setTitle(title);
        transaction.setDate(date);
        return transaction;
    }

    private Rate getRate(BigDecimal price) {
        Rate rate = new Rate();
        rate.setPrice(price);
        rate.setFromDate(LocalDate.of(2019, 10, 1));
        rate.setToDate(LocalDate.of(2022, 10, 1));
        return rate;
    }
    private void insertRate(Rate toInsert) {
        dsl.insertInto(RATES, RATES.FROM_DATE, RATES.TO_DATE, RATES.PRICE)
                .values(toInsert.getFromDate(), toInsert.getToDate(), toInsert.getPrice())
                .execute();
    }

}