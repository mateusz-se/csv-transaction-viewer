package com.mat.sek.transactions.api.csv;

import java.nio.file.Path;
import java.util.List;

public interface Parser {
    void parse(Path file);
}
