package com.oldnews.backend.app.controllers;

import com.oldnews.backend.app.enums.ArticleGetOrderByEnum;
import com.oldnews.backend.app.models.Article;
import com.oldnews.backend.app.repositories.ArticleRepository;
import com.oldnews.backend.common.controllers.BaseController;
import com.oldnews.backend.utils.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.chrono.IsoEra;

@RestController
@RequestMapping("/articles")
public class ArticleController extends BaseController<Article, ArticleRepository> {
    public ArticleController(ArticleRepository repository, ObjectMapperUtil mapper) {
        super(repository, mapper);
    }

    @Value("${app.pagination.size: 10}")
    private int pageSize;

    @GetMapping("/{month}/{day}")
    public ResponseEntity<Page<Article>> getByDayAndMonth(
            @PathVariable Integer month,
            @PathVariable Integer day,
            @RequestParam Integer page,
            @RequestParam ArticleGetOrderByEnum orderBy
    ){
        Pageable pageable = PageRequest.of(page, pageSize);

        LocalDate fromDate = LocalDate.of(1, month, day);
        LocalDate toDate = LocalDate.of(9999, month, day);

        Page<Article> articles;

        switch (orderBy) {
            case popularity -> articles = repository.findAllByDateBetweenAndDeletedIsFalseOrderByPopularityScoreDesc(
                    fromDate, toDate, pageable
            );

            case newer -> articles = repository.findAllByDateBetweenAndEraAndDeletedIsFalseOrderByDateDesc(
                    fromDate, toDate, IsoEra.CE, pageable
            );

            case older -> articles = repository.findAllByDateBetweenAndEraAndDeletedIsFalseOrderByDateAsc(
                    fromDate, toDate, IsoEra.BCE, pageable
            );

            default -> articles = Page.empty();
        }

        return ResponseEntity.ok(articles);
    }
}
