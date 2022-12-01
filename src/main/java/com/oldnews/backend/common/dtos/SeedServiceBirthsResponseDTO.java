package com.oldnews.backend.common.dtos;

import com.oldnews.backend.common.enums.ArticleTypesEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class SeedServiceBirthsResponseDTO extends SeedServiceBaseResponseDTO {
    private List<SeedServiceArticleDTO> births;

    @Override
    public List<SeedServiceArticleDTO> getArticles(){
        return this.births;
    }

    @Override
    public ArticleTypesEnum getArticleType(){
        return ArticleTypesEnum.births;
    }
}
