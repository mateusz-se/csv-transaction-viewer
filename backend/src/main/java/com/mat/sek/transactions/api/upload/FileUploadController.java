package com.mat.sek.transactions.api.upload;


import com.mat.sek.transactions.api.CsvFileType;
import com.mat.sek.transactions.api.csv.CsvParser;
import com.mat.sek.transactions.api.csv.CsvHandlerService;
import com.mat.sek.transactions.api.upload.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
public class FileUploadController {

    private final StorageService storageService;

    private final CsvHandlerService csvHandlerService;

    @Autowired
    public FileUploadController(StorageService storageService, CsvHandlerService csvHandlerService) {
        this.storageService = storageService;
        this.csvHandlerService = csvHandlerService;
    }

    @PostMapping("/{type}/upload")
    public FileUploadResponse uploadFile(@PathVariable CsvFileType type, @RequestParam("file") MultipartFile file) throws IOException {
        Resource loadedFile = storageService.store(type.name(), file);
        csvHandlerService.handle(type, loadedFile.getFile().toPath());
        String fileName = Objects.requireNonNull(loadedFile.getFilename());
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(type.name())
                .path("/file/")
                .path(fileName)
                .toUriString();

        return new FileUploadResponse(fileName, fileDownloadUri);
    }

}

