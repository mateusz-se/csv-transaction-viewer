package com.mat.sek.transactions.api.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class RateCsvParser implements Parser {

    private final CsvParser csvParser;

    @Autowired
    public RateCsvParser(CsvParser csvParser) {
        this.csvParser = csvParser;
    }

    @Override
    public void parse(Path file) {
        List<Rate> rates = this.csvParser.parse(Rate.class, file);
        System.out.println(rates);
    }
}
