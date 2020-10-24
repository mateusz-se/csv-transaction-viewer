package com.mat.sek.transactions.api.csv;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvNumber;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Rate {
    @CsvBindByPosition(position = 0)
    @CsvDate("yyyy-MM-dd")
    private LocalDate fromDate;

    @CsvBindByPosition(position = 1)
    @CsvDate("yyyy-MM-dd")
    private LocalDate toDate;

    @CsvBindByPosition(position = 2, format = "#.##")
    private BigDecimal price;
}
