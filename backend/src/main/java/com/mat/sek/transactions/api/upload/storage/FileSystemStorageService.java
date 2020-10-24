package com.mat.sek.transactions.api.upload.storage;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class FileSystemStorageService implements StorageService {
    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public Resource store(String dir, MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Path path = this.rootLocation.resolve(dir);
                if (!path.toFile().exists()) {
                    Files.createDirectory(path);
                } else {
                    FileUtils.cleanDirectory(path.toFile());
                }
                Path pathToFile = path.resolve(filename);
                Files.copy(inputStream, pathToFile,
                        StandardCopyOption.REPLACE_EXISTING);
                return loadAsResource(dir, filename);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Path load(String dir, String filename) {
        return rootLocation.resolve(dir).resolve(filename);
    }

    @Override
    public Resource loadAsResource(String dir, String filename) {
        try {
            Path file = load(dir, filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public Resource getLastLoaded(String dir) {
        Path path = rootLocation.resolve(dir);
        if (Files.isDirectory(path)) {
            try {
                Optional<URI> uri = Files.list(path)
                        .filter(p -> !Files.isDirectory(p))
                        .findFirst()
                        .map(Path::toUri);
                if (uri.isPresent()) {
                    return new UrlResource(uri.get());
                }
            } catch (IOException e) {
                throw new StorageException("Could load last file", e);
            }

        }
        return null;
    }
}
