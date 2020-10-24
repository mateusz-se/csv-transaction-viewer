package com.mat.sek.transactions.api.upload;


import com.mat.sek.transactions.api.csv.CsvFileType;
import com.mat.sek.transactions.api.csv.UploadHandlerService;
import com.mat.sek.transactions.api.upload.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
public class FileUploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

    private final StorageService storageService;

    private final UploadHandlerService uploadHandlerService;

    @Autowired
    public FileUploadController(StorageService storageService, UploadHandlerService uploadHandlerService) {
        this.storageService = storageService;
        this.uploadHandlerService = uploadHandlerService;
    }

    @PostMapping("/file/{type}")
    public FileUploadResponse uploadFile(@PathVariable CsvFileType type, @RequestParam("file") MultipartFile file) throws IOException {
        LOGGER.info("Starting {} file upload", type.name());
        Resource loadedFile = storageService.store(type.name(), file);
        uploadHandlerService.handle(type, loadedFile.getFile().toPath());
        String fileName = Objects.requireNonNull(loadedFile.getFilename());
        String fileDownloadUri = FileDownloadUtil.buildFileDownloadUrl(type.name().toLowerCase(), fileName);
        LOGGER.info("Uploaded file {}", fileName);
        return new FileUploadResponse(fileName, fileDownloadUri);
    }

}

