package com.mat.sek.transactions.api.csv;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    private BigDecimal amount;
    private String currency;
}
