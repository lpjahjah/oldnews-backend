package com.oldnews.backend.app.repositories;

import com.oldnews.backend.app.models.Article;
import com.oldnews.backend.common.enums.ArticleTypesEnum;
import com.oldnews.backend.common.repositories.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepository extends BaseRepository<Article> {
    Page<Article> findAllByDateMonthAndDateDayAndDeletedIsFalseOrderByPopularityScoreDesc(int month, int day, Pageable pageable);
    Page<Article> findAllByDateMonthAndDateDayAndArticleTypeAndDeletedIsFalseOrderByPopularityScoreDesc(int month, int day, ArticleTypesEnum articleType, Pageable pageable);

    Page<Article> findAllByDateMonthAndDateDayAndDeletedIsFalseOrderByDateEraDescDateYearDesc(int month, int day, Pageable pageable);
    Page<Article> findAllByDateMonthAndDateDayAndArticleTypeAndDeletedIsFalseOrderByDateEraDescDateYearDesc(int month, int day, ArticleTypesEnum articleType, Pageable pageable);

    Page<Article> findAllByDateMonthAndDateDayAndDeletedIsFalseOrderByDateEraAscDateYearAsc(int month, int day, Pageable pageable);
    Page<Article> findAllByDateMonthAndDateDayAndArticleTypeAndDeletedIsFalseOrderByDateEraAscDateYearAsc(int month, int day, ArticleTypesEnum articleType, Pageable pageable);

}
