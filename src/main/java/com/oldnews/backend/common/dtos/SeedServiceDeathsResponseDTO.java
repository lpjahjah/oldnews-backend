package com.oldnews.backend.common.dtos;

import com.oldnews.backend.common.enums.ArticleTypesEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class SeedServiceDeathsResponseDTO extends SeedServiceBaseResponseDTO {
    private List<SeedServiceArticleDTO> deaths;

    @Override
    public List<SeedServiceArticleDTO> getArticles(){
        return this.deaths;
    }

    @Override
    public ArticleTypesEnum getArticleType(){
        return ArticleTypesEnum.deaths;
    }
}
