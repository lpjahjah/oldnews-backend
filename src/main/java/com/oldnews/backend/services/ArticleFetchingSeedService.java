package com.oldnews.backend.services;

import com.oldnews.backend.utils.ArticleFetchingSeedServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ArticleFetchingSeedService {

    private final ArticleFetchingSeedServiceUtil config;

    private static final Logger log =
            LoggerFactory.getLogger(ArticleFetchingSeedService.class);

    private final List<LocalDate> dates = new ArrayList<>();

    public ArticleFetchingSeedService(ArticleFetchingSeedServiceUtil config) {
        this.config = config;
        LocalDate date = LocalDate.of(
                2020, 1, 1
        );

        dates.add(date);

        for (int i = 1; dates.get(dates.size()-1).getYear() == date.getYear(); i++){
            dates.add(date.plusDays(i));
        }
    }

    @PostConstruct
    public void run() {
        if (!config.isSeeder()) return;

        log.info("STARTING SEED");

        long time = System.nanoTime();

        config.fetchAllArticleTypes(
                dates,
                () -> {
                    long elapsedTime = TimeUnit.SECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS);
                    log.info(
                            String.format("SEED FINISHED SUCCESSFULLY IN %d SECONDS", elapsedTime)
                    );
                }
        );

    }
}
