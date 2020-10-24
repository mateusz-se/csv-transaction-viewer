package com.mat.sek.transactions.api.csv;

import java.nio.file.Path;

public interface FileHandler {
    void handle(Path file);
}
