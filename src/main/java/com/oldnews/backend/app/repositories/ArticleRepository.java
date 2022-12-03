package com.oldnews.backend.app.repositories;

import com.oldnews.backend.app.models.Article;
import com.oldnews.backend.common.repositories.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.chrono.IsoEra;

public interface ArticleRepository extends BaseRepository<Article> {
    Page<Article> findAllByDateBetweenAndDeletedIsFalseOrderByPopularityScoreDesc(LocalDate fromDate, LocalDate toDate, Pageable pageable);

    Page<Article> findAllByDateBetweenAndEraAndDeletedIsFalseOrderByDateDesc(LocalDate fromDate, LocalDate toDate, IsoEra era, Pageable pageable);

    Page<Article> findAllByDateBetweenAndEraAndDeletedIsFalseOrderByDateAsc(LocalDate fromDate, LocalDate toDate, IsoEra era, Pageable pageable);

}
