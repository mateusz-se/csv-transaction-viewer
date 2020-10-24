package com.mat.sek.transactions.api.csv;

import com.mat.sek.transactions.api.csv.converter.TextToPriceConverter;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Transaction {
    @CsvBindByPosition(position = 0)
    private Integer id;

    @CsvBindByPosition(position = 1)
    @CsvDate("yyyy-MM-dd")
    private LocalDate date;

    @CsvBindByPosition(position = 2)
    private String title;

    @CsvCustomBindByPosition(position = 3, converter = TextToPriceConverter.class)
    private Price price;

}
