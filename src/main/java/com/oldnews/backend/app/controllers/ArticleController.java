package com.oldnews.backend.app.controllers;

import com.oldnews.backend.app.models.Article;
import com.oldnews.backend.app.repositories.ArticleRepository;
import com.oldnews.backend.common.controllers.BaseController;
import com.oldnews.backend.utils.ObjectMapperUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticleController extends BaseController<Article, ArticleRepository> {
    public ArticleController(ArticleRepository repository, ObjectMapperUtil mapper) {
        super(repository, mapper);
    }
}
