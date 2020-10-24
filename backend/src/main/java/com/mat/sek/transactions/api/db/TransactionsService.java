package com.mat.sek.transactions.api.db;

import com.mat.sek.transactions.api.SearchParams;
import com.mat.sek.transactions.api.csv.Transaction;
import com.mat.sek.transactions.api.db.model.public_.tables.Rates;
import com.mat.sek.transactions.api.db.model.public_.tables.Transactions;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionsService {
    private final DSLContext dsl;

    @Autowired
    public TransactionsService(DSLContext dsl) {
        this.dsl = dsl;
    }

    public void insert(List<Transaction> transactionList) {
        Transactions transactions = Transactions.TRANSACTIONS;
        // TODO change to batch insert
        transactionList.forEach(v -> dsl.insertInto(transactions, transactions.ID,
                transactions.TITLE,
                transactions.TRANSACTION_DATE,
                transactions.PRICE,
                transactions.CURRENCY
        ).values(v.getId(),
                v.getTitle(),
                v.getDate(),
                v.getPrice().getAmount(),
                v.getPrice().getCurrency()
        ).execute());
    }

    public void removeAll() {
        dsl.deleteFrom(Transactions.TRANSACTIONS).execute();
    }

    public List<TransactionDTO> getTransactions(SearchParams searchParams) {
        Transactions transactions = Transactions.TRANSACTIONS;
        Rates rates = Rates.RATES;
        return dsl.select(transactions.ID,
                transactions.TITLE,
                transactions.TRANSACTION_DATE,
                transactions.PRICE.as("pricePln"),
                transactions.CURRENCY,
                transactions.PRICE.multiply(rates.PRICE).as("priceEur")
        )
                .from(transactions)
                .leftJoin(rates)
                .on(
                   transactions.TRANSACTION_DATE.between(rates.FROM_DATE, rates.TO_DATE)
                )
                .orderBy(transactions.TRANSACTION_DATE.desc())
                .offset((searchParams.getPage() - 1) * searchParams.getResults())
                .limit(searchParams.getResults())
                .fetch()
                .into(TransactionDTO.class);
    }
}
