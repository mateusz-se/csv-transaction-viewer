package com.mat.sek.transactions.api.upload.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
@Data
public class StorageProperties {

    private String location = "upload-dir";

}