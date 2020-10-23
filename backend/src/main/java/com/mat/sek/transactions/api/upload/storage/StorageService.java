package com.mat.sek.transactions.api.upload.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    void init();

    Resource store(String dir, MultipartFile file);

    Path load(String dir, String filename);

    Resource loadAsResource(String dir, String filename);

    void deleteAll();

    Resource getLastLoaded(String dir);
}
