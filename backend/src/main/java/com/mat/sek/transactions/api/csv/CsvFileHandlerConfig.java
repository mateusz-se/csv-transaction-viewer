package com.mat.sek.transactions.api.csv;

import com.mat.sek.transactions.api.csv.converter.StringToCsvFileTypeConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;

@Configuration
public class CsvFileHandlerConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToCsvFileTypeConverter());
    }

    @Bean
    Map<CsvFileType, FileHandler> parserMap(RateCsvFileHandler rateCsvParser, TransactionCsvFileHandler transactionCsvParser) {
        return Map.of(
                CsvFileType.RATE, rateCsvParser,
                CsvFileType.TRANSACTION, transactionCsvParser);
    }
}
