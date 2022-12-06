package com.oldnews.backend.common.dtos;

import com.oldnews.backend.app.models.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import reactor.core.publisher.Mono;

@Data
@AllArgsConstructor
public class FetchArticleImageDTO {
    private Mono<ImageResponseDTO> response;
    private Article article;
}
