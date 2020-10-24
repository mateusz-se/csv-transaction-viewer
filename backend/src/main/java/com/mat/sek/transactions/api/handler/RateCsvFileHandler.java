package com.mat.sek.transactions.api.handler;

import com.mat.sek.transactions.api.csv.Rate;
import com.mat.sek.transactions.api.csv.CsvParser;
import com.mat.sek.transactions.api.db.RatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class RateCsvFileHandler implements FileHandler {

    private final CsvParser csvParser;
    private final RatesService ratesService;

    @Autowired
    public RateCsvFileHandler(CsvParser csvParser, RatesService ratesService) {
        this.csvParser = csvParser;
        this.ratesService = ratesService;
    }

    @Override
    public void handle(Path file) {
        List<Rate> rates = this.csvParser.parse(Rate.class, file);
        ratesService.removeAll();
        ratesService.insert(rates);
    }
}
