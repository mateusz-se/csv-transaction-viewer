package com.mat.sek.transactions.api.upload;

import com.mat.sek.transactions.api.CsvFileType;
import com.mat.sek.transactions.api.upload.storage.FileSystemStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
public class FileDownloadController {

    private static final Logger logger = LoggerFactory.getLogger(FileDownloadController.class);

    private FileSystemStorageService fileSystemStorageService;

    @Autowired
    public FileDownloadController(FileSystemStorageService fileSystemStorageService) {
        this.fileSystemStorageService = fileSystemStorageService;
    }

    @GetMapping("{type}/file/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable CsvFileType type, @PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileSystemStorageService.loadAsResource(type.name(), fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("{type}/file/last")
    public FileUploadResponse lastLoadedFile(@PathVariable CsvFileType type,HttpServletRequest request) {
        Resource resource = fileSystemStorageService.getLastLoaded(type.name());
        String fileName = resource.getFilename();
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(type.name())
                .path("/file/")
                .path(fileName)
                .toUriString();

        return new FileUploadResponse(fileName, fileDownloadUri);
    }
}
