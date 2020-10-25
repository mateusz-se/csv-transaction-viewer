package com.mat.sek.transactions.api.db;

import com.mat.sek.transactions.api.SearchParams;
import com.mat.sek.transactions.api.csv.Transaction;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mat.sek.transactions.api.db.model.public_.tables.Rates.RATES;
import static com.mat.sek.transactions.api.db.model.public_.tables.Transactions.TRANSACTIONS;
import static org.jooq.impl.DSL.round;

@Repository
public class TransactionsRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionsRepository.class);

    private final DSLContext dsl;


    @Autowired
    public TransactionsRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public void insert(List<Transaction> transactionList) {
        LOGGER.info("Inserting {} transactions", transactionList.size());
        // TODO change to batch insert
        transactionList.forEach(v -> dsl.insertInto(TRANSACTIONS,
                TRANSACTIONS.ID,
                TRANSACTIONS.TITLE,
                TRANSACTIONS.TRANSACTION_DATE,
                TRANSACTIONS.PRICE,
                TRANSACTIONS.CURRENCY
        ).values(v.getId(),
                v.getTitle(),
                v.getDate(),
                v.getPrice().getAmount(),
                v.getPrice().getCurrency()
        ).execute());
    }

    public void removeAll() {
        LOGGER.info("Removing all transactions");
        dsl.deleteFrom(TRANSACTIONS).execute();
    }

    public List<TransactionDTO> getTransactions(SearchParams searchParams) {
        LOGGER.info("Getting transactions, {} page, {} limit", searchParams.getPage(), searchParams.getResults());
        return dsl.select(
                    TRANSACTIONS.ID,
                    TRANSACTIONS.TITLE,
                    TRANSACTIONS.TRANSACTION_DATE,
                    round(TRANSACTIONS.PRICE, 2).as("pricePln"),
                    TRANSACTIONS.CURRENCY,
                    round(TRANSACTIONS.PRICE.divide(RATES.PRICE), 2).as("priceEur")
                ).from(TRANSACTIONS)
                .leftJoin(RATES)
                .on(
                        TRANSACTIONS.TRANSACTION_DATE.between(RATES.FROM_DATE, RATES.TO_DATE)
                )
                .orderBy(TRANSACTIONS.TRANSACTION_DATE.desc())
                .offset((searchParams.getPage() - 1) * searchParams.getResults())
                .limit(searchParams.getResults())
                .fetch()
                .into(TransactionDTO.class);
    }

    public Integer getTransactionsCount() {
        LOGGER.info("Getting transactions count");
        return dsl.selectCount()
                .from(TRANSACTIONS)
                .fetchOne(0, Integer.class);
    }
}
