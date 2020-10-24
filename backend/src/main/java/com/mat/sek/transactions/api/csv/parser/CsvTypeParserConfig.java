package com.mat.sek.transactions.api.csv.parser;

import com.mat.sek.transactions.api.CsvFileType;
import com.mat.sek.transactions.api.csv.FileHandler;
import com.mat.sek.transactions.api.csv.RateCsvFileHandler;
import com.mat.sek.transactions.api.csv.TransactionCsvFileHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CsvTypeParserConfig {

    @Bean
    Map<CsvFileType, FileHandler> parserMap(RateCsvFileHandler rateCsvParser, TransactionCsvFileHandler transactionCsvParser) {
        return Map.of(
                CsvFileType.RATE, rateCsvParser,
                CsvFileType.TRANSACTION, transactionCsvParser);
    }
}
