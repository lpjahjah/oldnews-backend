package com.oldnews.backend.services.article.fetching;

import com.oldnews.backend.common.enums.ArticleTypesEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Component
public class ArticleFetchingBaseService {
    @Value("${app.article-seeder: true}")
    private boolean seeder;

    public boolean isSeeder() {
        return seeder;
    }

    private static final Logger log =
            LoggerFactory.getLogger(ArticleFetchingBaseService.class);

    private final WebClient client = WebClient.create("https://byabbe.se/on-this-day");

//    WAITING WHILE ARTICLE MODEL IS NOT READY
    private final Consumer<String> storeData = response -> {
        log.info(response);
    };

    private Flux<String> fetchArticles(LocalDate date, ArticleTypesEnum articleType) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                                .path("/{month}/{day}/" + articleType + ".json")
                                .build(date.getMonthValue(), date.getDayOfMonth())
                        )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(String.class);
    }

    /**
     * Fetch all articles for requested date.
     * Since this code is reactive, for logging purposes onError and onSuccess lambdas are requested.
     *
     * @param date LocalDate from witch the api will get the articles.
     * @param onError Consumer<Throwable> for logging on error.
     * @param onSuccess Runnable for logging on success.
     */
    public void fetchAllArticleTypes(
            LocalDate date,
            Consumer<Throwable> onError,
            Runnable onSuccess
    ) {
        Flux
                .fromIterable(Arrays.stream(ArticleTypesEnum.values()).toList())
                .flatMap(articleType -> fetchArticles(date, articleType))
                .subscribe(storeData, onError, onSuccess);
    }

    /**
     * Fetch all articles for requested dates, better perform when fetching multiples dates at once.
     * Since this code is reactive, for logging purposes onError and onSuccess lambdas are requested.
     *
     * @param dates List<LocalDate> from witch the api will get the articles.
     * @param onError Consumer<Throwable> for logging on error.
     * @param onSuccess Runnable for logging on success.
     */
    public void fetchAllArticleTypes(
            List<LocalDate> dates,
            Consumer<Throwable> onError,
            Runnable onSuccess
    ) {
        Flux
                .fromIterable(Arrays.stream(ArticleTypesEnum.values()).toList())
                .flatMap(articleType -> {
                    return Flux
                            .fromIterable(dates)
                            .flatMap(date -> {
                                return fetchArticles(date, articleType);
                            });
                }).subscribe(storeData, onError, onSuccess);
    }

//    NOT NECESSARY

//    public void fetchEvents(LocalDate date) {
//        fetchArticles(date, ArticleTypesEnum.events).subscribe(storeData);
//    }
//
//    public void fetchDeaths(LocalDate date) {
//        fetchArticles(date, ArticleTypesEnum.deaths).subscribe(storeData);
//    }
//
//    public void fetchBirths(LocalDate date) {
//        fetchArticles(date, ArticleTypesEnum.births).subscribe(storeData);
//    }
}
