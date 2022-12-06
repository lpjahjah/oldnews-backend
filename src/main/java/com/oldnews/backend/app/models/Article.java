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
import java.time.LocalDateTime;
import java.time.MonthDay;
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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "year", column = @Column(name = "date_year")),
            @AttributeOverride( name = "month", column = @Column(name = "date_month")),
            @AttributeOverride( name = "day", column = @Column(name = "date_day")),
            @AttributeOverride( name = "era", column = @Column(name = "date_era"))
    })
    private ArticleDate date;

    @Column(name = "source", nullable = false)
    private String source;

    @Column(name = "description", nullable = false, columnDefinition = "text")
    private String description;

    @Column(name = "popularity_score", nullable = false)
    private Integer popularityScore = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "article_type")
    private ArticleTypesEnum articleType;

    @Column(name = "image_source")
    private String imageSource;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;
    @Override
    public boolean getDeleted() {
        return false;
    }

    @Override
    public void setDeleted(boolean deleted) {
    }

    public void setDateFromSeed(String year, MonthDay monthDay){
        int formattedYear = Integer.parseInt(
                year.replaceAll("[\\D]", "")
        );

        this.date = new ArticleDate(
                formattedYear,
                monthDay,
                year.contains("BC") ? IsoEra.BCE : IsoEra.CE
        );
    }

    public static Article fillFromSeed(
            MonthDay monthDay,
            ArticleTypesEnum articleType,
            SeedServiceArticleDTO seedItem
    ) throws Exception {

        SeedServiceWikipediaDTO firstWikipediaItem = seedItem.getWikipedia().size() > 0
            ? seedItem.getWikipedia().get(0)
                : null;

        Article article = new Article();
        article.setDateFromSeed(seedItem.getYear(), monthDay);
        article.setTitle(firstWikipediaItem != null ? firstWikipediaItem.getTitle() : "");
        article.setSource(firstWikipediaItem != null ? firstWikipediaItem.getWikipedia() : "");
        article.setDescription(seedItem.getDescription() != null ? seedItem.getDescription() : "");
        article.setArticleType(articleType);


        return article;
    }
}
