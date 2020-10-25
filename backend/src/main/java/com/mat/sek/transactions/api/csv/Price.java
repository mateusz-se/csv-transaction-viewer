package com.mat.sek.transactions.api.csv;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
public class Price {
    private final BigDecimal amount;
    private final String currency;
}
