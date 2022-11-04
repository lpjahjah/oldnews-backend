package com.oldnews.backend.services.article.fetching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ArticleFetchingScheduleService {
    private final ArticleFetchingBaseService config;

    private static final Logger log =
            LoggerFactory.getLogger(ArticleFetchingScheduleService.class);

    public ArticleFetchingScheduleService(ArticleFetchingBaseService config) {
        this.config = config;
    }

    //    @Scheduled(cron = "0 0 * * *")
    @Scheduled(fixedRate = 1000)
    private void fetch() {
        if (config.isSeeder()) return;

        log.info("Running");
    }

}
