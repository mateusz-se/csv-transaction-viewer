package com.mat.sek.transactions.api.csv;

import com.mat.sek.transactions.api.CsvFileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Service
public class CsvHandlerService {
    private final Map<CsvFileType, Parser> parserMap;

    @Autowired
    public CsvHandlerService(Map<CsvFileType, Parser> parserMap) {
        this.parserMap = parserMap;
    }

    public void handle(CsvFileType type, Path path) {
        this.parserMap.get(type).parse(path);
    }
}
