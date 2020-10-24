package com.mat.sek.transactions.api;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchParams {
    private Integer page;
    private Integer results;
}
