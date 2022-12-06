package com.oldnews.backend.migrations;

import com.oldnews.backend.app.models.Article;
import com.oldnews.backend.app.repositories.ArticleRepository;
import com.oldnews.backend.common.dtos.ImageResponseDTO;
import com.oldnews.backend.common.dtos.ImageResponseQueryPageDTO;
import com.oldnews.backend.common.migrations.Migration;
import com.oldnews.backend.utils.ArticleImageFetchingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;

@Component
public class Migration1 implements Migration {
    private final Logger log =
            LoggerFactory.getLogger(Migration1.class);

    private final ArticleImageFetchingUtil imageFetchingUtil;

    private final ArticleRepository articleRepository;

    public Migration1(ArticleImageFetchingUtil imageFetchingUtil, ArticleRepository articleRepository) {
        this.imageFetchingUtil = imageFetchingUtil;
        this.articleRepository = articleRepository;
    }

    @Override
    public int getMigrationNumber() {
        return 1;
    }

    @Override
    public String getMigrationDescription() {
        return "Migration that will fill the new article image attr";
    }

    private Consumer<ImageResponseDTO> finish() {
        return response -> {
            if (response.getQuery() == null || response.getQuery().getPages() == null)
                return;

            ImageResponseQueryPageDTO page =
                    response.getQuery().getPages().stream().findFirst().orElse(null);

            if (page == null || page.getOriginal() == null)
                return;

            Article article = response.getArticle();

            article.setImageSource(page.getOriginal().getSource());
            articleRepository.save(article);

            log.info(String.format("Article image fetched: %s", article.getImageSource()));
        };
    }

    @Override
    public Mono<?> run() {
        List<Article> articles = articleRepository.findAll();

        return Flux
                .fromIterable(articles)
                .flatMap(imageFetchingUtil::fetchArticleImage)
                .delayElements(Duration.ofSeconds(5))
                .limitRate(10)
                .doOnNext(finish())
                .then(Mono.empty());
    }
}
