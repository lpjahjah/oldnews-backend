package com.oldnews.backend.common.dtos;

import lombok.Data;

import java.util.List;

@Data
public class SeedServiceArticleDTO {
    private String year;

    private String description;

    private List<SeedServiceWikipediaDTO> wikipedia;
}
