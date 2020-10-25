package com.mat.sek.transactions.api.handler;

import com.mat.sek.transactions.api.csv.CsvParser;
import com.mat.sek.transactions.api.csv.Rate;
import com.mat.sek.transactions.api.db.RatesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateCsvFileHandlerTest {


    @Mock
    private CsvParser csvParser;

    @Mock
    private RatesRepository ratesRepository;

    @InjectMocks
    private RateCsvFileHandler rateCsvFileHandler;

    @Test
    void handle() {
        Path pathToCsvFile = Path.of("src", "test");
        List<Rate> rates = List.of(createRate(1d),
                createRate(2d));
        when(csvParser.parse(Rate.class, pathToCsvFile)).thenReturn(
                rates
        );
        rateCsvFileHandler.handle(pathToCsvFile);
        verify(ratesRepository, times(1)).removeAll();
        verify(ratesRepository, times(1)).insert(rates);
    }

    private Rate createRate(double price) {
        Rate rate = new Rate();
        rate.setPrice(BigDecimal.valueOf(price));
        return rate;
    }
}