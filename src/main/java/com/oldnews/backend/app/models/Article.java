package com.oldnews.backend.app.models;

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

    @Column(name = "source", nullable = false)
    private String source;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

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
}
