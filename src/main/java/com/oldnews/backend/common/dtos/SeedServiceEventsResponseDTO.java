package com.oldnews.backend.common.dtos;

import com.oldnews.backend.common.enums.ArticleTypesEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class SeedServiceEventsResponseDTO extends SeedServiceBaseResponseDTO {
    private List<SeedServiceArticleDTO> events;

    @Override
    public List<SeedServiceArticleDTO> getArticles(){
        return this.events;
    }

    @Override
    public ArticleTypesEnum getArticleType(){
        return ArticleTypesEnum.events;
    }
}
