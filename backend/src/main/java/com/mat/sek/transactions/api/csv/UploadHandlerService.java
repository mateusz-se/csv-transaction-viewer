package com.mat.sek.transactions.api.csv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Map;

@Service
public class UploadHandlerService {
    private final Map<CsvFileType, FileHandler> handlerMap;

    @Autowired
    public UploadHandlerService(Map<CsvFileType, FileHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public void handle(CsvFileType type, Path path) {
        this.handlerMap.get(type).handle(path);
    }
}
