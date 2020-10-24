package com.mat.sek.transactions.api.upload;

import com.mat.sek.transactions.api.csv.CsvFileType;
import com.mat.sek.transactions.api.upload.storage.FileSystemStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class FileDownloadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileDownloadController.class);

    private final FileSystemStorageService fileSystemStorageService;

    @Autowired
    public FileDownloadController(FileSystemStorageService fileSystemStorageService) {
        this.fileSystemStorageService = fileSystemStorageService;
    }

    @GetMapping("/file/{type}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable CsvFileType type, @PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileSystemStorageService.loadAsResource(type.name(), fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            LOGGER.info("Could not determine file type.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/file/{type}/last")
    public ResponseEntity<FileUploadResponse> lastLoadedFile(@PathVariable CsvFileType type, HttpServletRequest request) {
        Resource resource = fileSystemStorageService.getLastLoaded(type.name());
        if (resource != null && resource.exists()) {
            String fileName = resource.getFilename();
            String fileDownloadUri = FileDownloadUtil.buildFileDownloadUrl(type.name().toLowerCase(), fileName);
            return new ResponseEntity<>(
                    new FileUploadResponse(fileName, fileDownloadUri),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
