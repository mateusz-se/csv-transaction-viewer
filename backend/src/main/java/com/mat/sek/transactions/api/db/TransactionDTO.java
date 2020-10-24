package com.mat.sek.transactions.api.db;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionDTO {
    private Integer id;
    private String title;
    private LocalDate transactionDate;
    private BigDecimal pricePln;
    private BigDecimal priceEur;
}
