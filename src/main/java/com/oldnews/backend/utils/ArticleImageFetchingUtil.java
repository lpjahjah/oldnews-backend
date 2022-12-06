package com.oldnews.backend.utils;

import com.oldnews.backend.app.models.Article;
import com.oldnews.backend.common.dtos.ImageResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.SslProvider;

import java.time.Duration;

@Component
public class ArticleImageFetchingUtil {

    private final WebClient client;

    private final Logger log =
            LoggerFactory.getLogger(ArticleImageFetchingUtil.class);

    public ArticleImageFetchingUtil() {

        HttpClient httpClient = HttpClient.create()
                .secure(spec -> spec.
                        sslContext(SslProvider.defaultClientProvider().getSslContext())
                        .handshakeTimeout(Duration.ofSeconds(30)));

        this.client = WebClient.builder()
                .baseUrl("https://en.wikipedia.org/w/api.php")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    public Mono<ImageResponseDTO> fetchArticleImage(Article article) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("action", "query")
                        .queryParam("prop", "pageimages")
                        .queryParam("format", "json")
                        .queryParam("piprop", "original")
                        .queryParam("formatversion", "2")
                        .queryParam("titles", article.getTitle())
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ImageResponseDTO.class)
                .map(res -> {
                    res.setArticle(article);
                    return res;
                });
    }
}
