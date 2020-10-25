package com.mat.sek.transactions.api.db;

import com.mat.sek.transactions.api.SpringBootApplication;
import com.mat.sek.transactions.api.csv.Rate;
import com.mat.sek.transactions.api.db.model.public_.tables.Rates;
import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.mat.sek.transactions.api.db.model.public_.tables.Rates.RATES;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApplication.class)
class RatesRepositoryTest {

    @Autowired
    private RatesRepository ratesRepository;

    @Autowired
    private DSLContext dsl;

    @Test
    void insertRatesIntoDB() {
        List<Rate> ratesToInsert = List.of(getRate(BigDecimal.TEN, 10, 12),
                getRate(BigDecimal.valueOf(4d), 13, 14));

        ratesRepository.insert(ratesToInsert);

        List<Rate> fetchedRates = fetchRates();
        assertEquals(ratesToInsert, fetchedRates);
    }

    @Test
    void removeAllRatesFromDB() {
        Rate toInsert = getRate(BigDecimal.ONE, 10, 22);
        dsl.insertInto(RATES, RATES.FROM_DATE, RATES.TO_DATE, RATES.PRICE)
                .values(toInsert.getFromDate(), toInsert.getToDate(), toInsert.getPrice())
                .execute();

        ratesRepository.removeAll();

        List<Rate> fetchedRates = fetchRates();
        assertEquals(0, fetchedRates.size());
    }

    private List<Rate> fetchRates() {
        return dsl.select()
                .from(RATES)
                .fetch()
                .into(Rate.class);
    }

    private Rate getRate(BigDecimal price, int fromDayOfMonth, int toDayOfMonth) {
        Rate rate = new Rate();
        rate.setPrice(price);
        rate.setFromDate(LocalDate.of(2020, 10, fromDayOfMonth));
        rate.setToDate(LocalDate.of(2020, 10, toDayOfMonth));
        return rate;
    }
}