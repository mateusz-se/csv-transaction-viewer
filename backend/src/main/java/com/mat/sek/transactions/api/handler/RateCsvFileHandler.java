package com.mat.sek.transactions.api.handler;

import com.mat.sek.transactions.api.csv.Rate;
import com.mat.sek.transactions.api.csv.CsvParser;
import com.mat.sek.transactions.api.db.RatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
class RateCsvFileHandler implements FileHandler {

    private final CsvParser csvParser;
    private final RatesRepository ratesRepository;

    @Autowired
    RateCsvFileHandler(CsvParser csvParser, RatesRepository ratesRepository) {
        this.csvParser = csvParser;
        this.ratesRepository = ratesRepository;
    }

    @Override
    public void handle(Path file) {
        List<Rate> rates = this.csvParser.parse(Rate.class, file);
        ratesRepository.removeAll();
        ratesRepository.insert(rates);
    }
}
