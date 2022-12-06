package com.oldnews.backend.common.dtos;

import com.oldnews.backend.app.models.Article;
import lombok.Data;

@Data
public class ImageResponseDTO {
    private Article article;
    private ImageResponseQueryDTO query;
}
