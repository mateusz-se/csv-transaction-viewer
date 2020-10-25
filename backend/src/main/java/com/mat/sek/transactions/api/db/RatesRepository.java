package com.mat.sek.transactions.api.db;

import com.mat.sek.transactions.api.csv.Rate;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mat.sek.transactions.api.db.model.public_.tables.Rates.RATES;

@Repository
public class RatesRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(RatesRepository.class);

    private final DSLContext dsl;

    @Autowired
    public RatesRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public void insert(List<Rate> rateList) {
        LOGGER.info("Inserting {} rates", rateList.size());
        rateList.forEach(v -> {
            dsl.insertInto(RATES, RATES.FROM_DATE, RATES.TO_DATE, RATES.PRICE)
                    .values(v.getFromDate(), v.getToDate(), v.getPrice())
                    .execute();
        });
    }

    public void removeAll() {
        LOGGER.info("Removing all rates");
        dsl.deleteFrom(RATES).execute();
    }
}
