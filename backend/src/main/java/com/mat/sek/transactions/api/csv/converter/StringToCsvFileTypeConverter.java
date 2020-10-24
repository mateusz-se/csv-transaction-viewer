package com.mat.sek.transactions.api.csv.converter;

import com.mat.sek.transactions.api.csv.CsvFileType;
import org.springframework.core.convert.converter.Converter;

public class StringToCsvFileTypeConverter implements Converter<String, CsvFileType> {
    @Override
    public CsvFileType convert(String source) {
        return CsvFileType.valueOf(source.toUpperCase());
    }
}
