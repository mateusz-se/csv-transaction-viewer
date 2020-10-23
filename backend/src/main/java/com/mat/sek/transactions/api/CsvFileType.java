package com.mat.sek.transactions.api;

import com.mat.sek.transactions.api.csv.Rate;
import com.mat.sek.transactions.api.csv.Transaction;

public enum CsvFileType {
    TRANSACTION(Transaction.class),
    RATE(Rate.class);

    private Class clazz;

    CsvFileType(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }
}
