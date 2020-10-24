package com.mat.sek.transactions.api.csv;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CsvFileHandlerConfig {

    @Bean
    Map<CsvFileType, FileHandler> parserMap(RateCsvFileHandler rateCsvParser, TransactionCsvFileHandler transactionCsvParser) {
        return Map.of(
                CsvFileType.RATE, rateCsvParser,
                CsvFileType.TRANSACTION, transactionCsvParser);
    }
}
