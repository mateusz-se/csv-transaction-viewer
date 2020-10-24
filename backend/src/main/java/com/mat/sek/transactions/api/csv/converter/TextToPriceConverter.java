package com.mat.sek.transactions.api.csv.converter;

import com.mat.sek.transactions.api.csv.Price;
import com.opencsv.bean.AbstractBeanField;
import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import java.math.BigDecimal;

public class TextToPriceConverter extends AbstractBeanField {
    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        Price p = new Price();
        int index = value.lastIndexOf(" ");
        p.setAmount(new BigDecimal(value.substring(0, index)));
        p.setCurrency(value.substring(index + 1));
        return p;
    }
}
