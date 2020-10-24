package com.mat.sek.transactions.api.db;

import com.mat.sek.transactions.api.csv.Rate;
import com.mat.sek.transactions.api.csv.Transaction;
import com.mat.sek.transactions.api.db.model.public_.tables.Rates;
import com.mat.sek.transactions.api.db.model.public_.tables.Transactions;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatesService {
    private final DSLContext dsl;

    @Autowired
    public RatesService(DSLContext dsl) {
        this.dsl = dsl;
    }

    public void insert(List<Rate> rateList) {
        Rates rates = Rates.RATES;
        rateList.forEach(v -> {
            dsl.insertInto(rates, rates.FROM_DATE, rates.TO_DATE, rates.PRICE)
                    .values(v.getFromDate(), v.getToDate(), v.getPrice())
                    .execute();
        });
    }

    public void removeAll() {
        dsl.deleteFrom(Rates.RATES).execute();
    }
}
