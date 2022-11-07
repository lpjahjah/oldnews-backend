package com.oldnews.backend.app.models;

import com.oldnews.backend.common.enums.ArticleTypesEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="article")
public class Article {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "source", nullable = false)
    private String source;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "article_type")
    private ArticleTypesEnum articleType;
}
