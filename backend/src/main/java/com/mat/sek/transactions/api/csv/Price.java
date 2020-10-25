package com.mat.sek.transactions.api.csv;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Price {
    private final BigDecimal amount;
    private final String currency;
}
