package com.oldnews.backend.app.controllers;

import com.oldnews.backend.app.dtos.ExceptionResponseDto;
import com.oldnews.backend.app.enums.ArticleGetOrderByEnum;
import com.oldnews.backend.app.models.Article;
import com.oldnews.backend.app.repositories.ArticleRepository;
import com.oldnews.backend.common.controllers.BaseController;
import com.oldnews.backend.common.enums.ArticleTypesEnum;
import com.oldnews.backend.utils.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/articles")
public class ArticleController extends BaseController<Article, ArticleRepository> {
    public ArticleController(ArticleRepository repository, ObjectMapperUtil mapper) {
        super(repository, mapper);
    }

    @Value("${app.pagination.size: 10}")
    private int pageSize;

    @GetMapping("/{month}/{day}")
    public ResponseEntity<?> getByDayAndMonth(
            @PathVariable int month,
            @PathVariable int day,
            @RequestParam int page,
            @RequestParam(required = false) ArticleTypesEnum articleType,
            @RequestParam(required = false, defaultValue = "popularity") ArticleGetOrderByEnum orderBy,
            HttpServletRequest req
    ){
        if (month > 12 || day > 31)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ExceptionResponseDto(
                            "Date not valid",
                            "Month or day are not valid.",
                            req.getRequestURI()
                    )
            );

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Article> articles = articleType != null
                ? searchWithType(month, day, pageable, articleType, orderBy)
                : searchWithoutType(month, day, pageable, orderBy);

        return ResponseEntity.ok(articles);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> enumException(HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionResponseDto(
                        "Params exception",
                        "One or more params are not valid",
                        req.getRequestURI()
                )
        );
    }

    private Page<Article> searchWithType(int month, int day, Pageable pageable, ArticleTypesEnum articleType, ArticleGetOrderByEnum orderBy) {
        return switch (orderBy) {
            case popularity -> repository.findAllByDateMonthAndDateDayAndArticleTypeAndDeletedIsFalseOrderByPopularityScoreDesc(
                    month, day, articleType, pageable
            );

            case newer -> repository.findAllByDateMonthAndDateDayAndArticleTypeAndDeletedIsFalseOrderByDateEraDescDateYearDesc(
                    month, day, articleType, pageable
            );

            case older -> repository.findAllByDateMonthAndDateDayAndArticleTypeAndDeletedIsFalseOrderByDateEraAscDateYearAsc(
                    month, day, articleType, pageable
            );

            default -> Page.empty();
        };
    }

    private Page<Article> searchWithoutType(int month, int day, Pageable pageable, ArticleGetOrderByEnum orderBy) {
        return switch (orderBy) {
            case popularity -> repository.findAllByDateMonthAndDateDayAndDeletedIsFalseOrderByPopularityScoreDesc(
                    month, day, pageable
            );

            case newer -> repository.findAllByDateMonthAndDateDayAndDeletedIsFalseOrderByDateEraDescDateYearDesc(
                    month, day, pageable
            );

            case older -> repository.findAllByDateMonthAndDateDayAndDeletedIsFalseOrderByDateEraAscDateYearAsc(
                    month, day, pageable
            );

            default -> Page.empty();
        };
    }
}
