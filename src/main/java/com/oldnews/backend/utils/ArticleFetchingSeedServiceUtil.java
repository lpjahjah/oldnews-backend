package com.oldnews.backend.utils;

import com.oldnews.backend.app.models.Article;
import com.oldnews.backend.app.repositories.ArticleRepository;
import com.oldnews.backend.common.dtos.*;
import com.oldnews.backend.common.enums.ArticleTypesEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.SslProvider;

import java.time.Duration;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Component
public class ArticleFetchingSeedServiceUtil {
    @Value("${app.article-seeder: true}")
    private boolean seeder;

    public boolean isSeeder() {
        return seeder;
    }

    private final ArticleRepository articleRepository;

    private static final Logger log =
            LoggerFactory.getLogger(ArticleFetchingSeedServiceUtil.class);

    private final WebClient client;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d", Locale.ENGLISH);

    public ArticleFetchingSeedServiceUtil(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;

        HttpClient httpClient = HttpClient.create()
                .secure(spec -> spec.
                        sslContext(SslProvider.defaultClientProvider().getSslContext())
                        .handshakeTimeout(Duration.ofSeconds(30)));

        this.client = WebClient.builder()
                .baseUrl("https://byabbe.se/on-this-day")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    public ArticleRepository getArticleRepository() {
        return articleRepository;
    }

    private final Consumer<SeedServiceBaseResponseDTO> storeData = response -> {
        MonthDay date = MonthDay.parse(response.getDate(), formatter);
        Integer articlesStored = 0;

        List<Article> articles = new ArrayList<Article>();

        for (SeedServiceArticleDTO seedArticle: response.getArticles()){
            try {
                Article article = Article.fillFromSeed(date, response.getArticleType(), seedArticle);
                articles.add(article);
                articlesStored++;
            } catch (Exception e){
                log.error("Error while filling record: ", e);
            }
        }

        try {
            getArticleRepository().saveAll(articles);
        } catch (Exception e){
            log.error("Error while storing record to database: ", e);
        }

        log.info(String.format("Articles stored: %d", articlesStored));
    };

    private final BiConsumer<Throwable, Object> onError = (throwable, o) -> {
        log.error("Error while processing {}. Cause: {}", o, throwable.getMessage());
    };

    private Class<? extends SeedServiceBaseResponseDTO> getTypeClass(ArticleTypesEnum articleType){
        return switch (articleType) {
            case births -> SeedServiceBirthsResponseDTO.class;
            case deaths -> SeedServiceDeathsResponseDTO.class;
            case events -> SeedServiceEventsResponseDTO.class;
        };
    }

    private Flux<? extends SeedServiceBaseResponseDTO> fetchArticles(LocalDate date, ArticleTypesEnum articleType) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                                .path(String.format("/{month}/{day}/%s.json", articleType))
                                .build(date.getMonthValue(), date.getDayOfMonth())
                        )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(getTypeClass(articleType));
    }

    /**
     * Fetch all articles for requested date.
     * Since this code is reactive, for logging purposes onError and onSuccess lambdas are requested.
     *
     * @param date LocalDate from witch the api will get the articles.
     * @param onComplete Runnable for logging on completion.
     */
    public void fetchAllArticleTypes(
            LocalDate date,
            Runnable onComplete
    ) {
        Flux
                .fromIterable(Arrays.stream(ArticleTypesEnum.values()).toList())
                .flatMap(articleType ->
                        fetchArticles(date, articleType)
                                .onErrorContinue(onError)
                                .doAfterTerminate(onComplete)
                )
                .onErrorContinue(onError)
                .subscribe(storeData);
    }

    /**
     * Fetch all articles for requested dates, better perform when fetching multiples dates at once.
     * Since this code is reactive, for logging purposes onError and onSuccess lambdas are requested.
     *
     * @param dates List<LocalDate> from witch the api will get the articles.
     * @param onComplete Runnable for logging on completion.
     */
    public void fetchAllArticleTypes(
            List<LocalDate> dates,
            Runnable onComplete
    ) {
        Flux
                .fromIterable(Arrays.stream(ArticleTypesEnum.values()).toList())
                .flatMap(articleType -> Flux
                            .fromIterable(dates)
                            .flatMap(date ->
                                    fetchArticles(date, articleType)
                            )
                                .onErrorContinue(onError)
                                .doAfterTerminate(onComplete)
                )
                .onErrorContinue(onError)
                .subscribe(storeData);
    }
}
