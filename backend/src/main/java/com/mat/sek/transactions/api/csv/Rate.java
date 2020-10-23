package com.mat.sek.transactions.api.csv;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class Rate {
    @CsvBindByPosition(position = 0)
    @CsvDate("yyyy-MM-dd")
    private LocalDate fromDate;
    @CsvBindByPosition(position = 1)
    @CsvDate("yyyy-MM-dd")
    private LocalDate toDate;
    @CsvBindByPosition(position = 2)
    private String price;
}
