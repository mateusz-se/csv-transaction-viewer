package com.mat.sek.transactions.api.upload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class FileUploadResponse {
    private String name;
    private String url;
}
