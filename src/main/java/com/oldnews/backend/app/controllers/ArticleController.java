package com.oldnews.backend.app.controllers;

import com.oldnews.backend.app.models.Article;
import com.oldnews.backend.app.repositories.ArticleRepository;
import com.oldnews.backend.common.controllers.BaseController;
import com.oldnews.backend.utils.ObjectMapperUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ArticleController extends BaseController<Article, ArticleRepository> {

    public ArticleController(ArticleRepository repository, ObjectMapperUtil mapper) {
        super(repository, mapper);
    }

    @PostMapping("/test")
    public ResponseEntity<?> Test() {
        return ResponseEntity.ok().build();
    }
}
