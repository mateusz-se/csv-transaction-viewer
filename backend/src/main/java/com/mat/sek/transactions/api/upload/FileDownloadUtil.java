package com.mat.sek.transactions.api.upload;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class FileDownloadUtil {
    public static String buildFileDownloadUrl(String type, String fileName) {
        return  ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(type)
                .path("/file/")
                .path(fileName)
                .toUriString();
    }
}
