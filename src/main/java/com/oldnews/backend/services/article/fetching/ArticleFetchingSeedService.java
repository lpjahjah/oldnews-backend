package com.oldnews.backend.services.article.fetching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Service
public class ArticleFetchingSeedService {

    private final ArticleFetchingBaseService config;

    private static final Logger log =
            LoggerFactory.getLogger(ArticleFetchingSeedService.class);

    private final LocalDate date = LocalDate.of(
            LocalDate.now().getYear(), 1, 1
    );

    public ArticleFetchingSeedService(ArticleFetchingBaseService config) {
        this.config = config;
    }

    @PostConstruct
    public void run() {
        if (!config.isSeeder()) return;

        log.info("STARTING SEED");

        config.fetchAllArticleTypes(
                date,
                err -> log.error("ERROR WHILE SEEDING", err),
                () -> log.info("SEED FINISHED SUCCESSFULLY")
        );

    }
}
