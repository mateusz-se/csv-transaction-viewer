package com.mat.sek.transactions.api.db;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TransactionCountDTO {
    private Integer count;
}
