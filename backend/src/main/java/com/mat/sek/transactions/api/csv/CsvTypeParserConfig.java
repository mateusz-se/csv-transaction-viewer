package com.mat.sek.transactions.api.csv;

import com.mat.sek.transactions.api.CsvFileType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CsvTypeParserConfig {

    @Bean
    Map<CsvFileType, Parser> parserMap(RateCsvParser rateCsvParser, TransactionCsvParser transactionCsvParser) {
        return Map.of(
                CsvFileType.RATE, rateCsvParser,
                CsvFileType.TRANSACTION, transactionCsvParser);
    }
}
