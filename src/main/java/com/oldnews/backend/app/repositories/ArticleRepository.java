package com.oldnews.backend.app.repositories;

import com.oldnews.backend.app.models.Article;
import com.oldnews.backend.common.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends BaseRepository<Article> {

}
