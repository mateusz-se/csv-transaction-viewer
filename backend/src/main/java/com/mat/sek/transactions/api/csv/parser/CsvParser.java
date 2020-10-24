package com.mat.sek.transactions.api.csv.parser;

import com.mat.sek.transactions.api.csv.TransactionCsvFileHandler;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class CsvParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvParser.class);

    public <T> List<T> parse(Class<T> clazz,Path file) {
        try (
                Reader reader = Files.newBufferedReader(file);
        ) {
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withType(clazz)
                    .build();

            return csvToBean.parse();
        } catch (IOException e) {
            LOGGER.error("Error parsing file", e);
        }
        return List.of();
    }


}
