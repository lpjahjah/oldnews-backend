package com.oldnews.backend.app.models;

import com.oldnews.backend.common.dtos.SeedServiceArticleDTO;
import com.oldnews.backend.common.dtos.SeedServiceWikipediaDTO;
import com.oldnews.backend.common.enums.ArticleTypesEnum;
import com.oldnews.backend.common.models.BaseModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.IsoEra;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="article")
public class Article implements BaseModel {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "era", nullable = false)
    private IsoEra era = IsoEra.CE;

    @Column(name = "source", nullable = false)
    private String source;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "popularity_score", nullable = false)
    private Integer popularityScore = 0;

    @Column(name = "article_type")
    private ArticleTypesEnum articleType;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted")
    private boolean deleted;
    @Override
    public boolean getDeleted() {
        return false;
    }

    @Override
    public void setDeleted(boolean deleted) {
    }

    public static Article fillFromSeed(
            LocalDate date,
            ArticleTypesEnum articleType,
            SeedServiceArticleDTO seedItem
    ) throws Exception {

        int formattedYear = Integer.parseInt(
                seedItem.getYear().replaceAll("[\\D]", "")
        );

        date = LocalDate.of(
                formattedYear,
                date.getMonthValue(),
                date.getDayOfMonth()
        );

        SeedServiceWikipediaDTO firstWikipediaItem = seedItem.getWikipedia().size() > 0
            ? seedItem.getWikipedia().get(0)
                : null;

        Article article = new Article();
        article.setDate(date);
        article.setEra(seedItem.getYear().contains("BC") ? IsoEra.BCE : IsoEra.CE);
        article.setTitle(firstWikipediaItem != null ? firstWikipediaItem.getTitle() : "");
        article.setSource(firstWikipediaItem != null ? firstWikipediaItem.getWikipedia() : "");
        article.setDescription(seedItem.getDescription() != null ? seedItem.getDescription() : "");
        article.setArticleType(articleType);


        return article;
    }
}
