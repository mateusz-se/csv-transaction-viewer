package com.mat.sek.transactions.api.csv;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class Transaction {
    @CsvBindByPosition(position = 0)
    private long id;
    @CsvBindByPosition(position = 1)
    @CsvDate("yyyy-MM-dd")
    private LocalDate date;
    @CsvBindByPosition(position = 2)
    private String title;
    @CsvBindByPosition(position = 3)
    private String amount;

}
