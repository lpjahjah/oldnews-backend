package com.oldnews.backend.common.dtos;

import com.oldnews.backend.common.enums.ArticleTypesEnum;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public abstract class SeedServiceBaseResponseDTO {
    private String date;

    private String wikipedia;

    public List<SeedServiceArticleDTO> getArticles(){
        return Collections.emptyList();
    }

    public ArticleTypesEnum getArticleType(){
        return ArticleTypesEnum.events;
    }
}
