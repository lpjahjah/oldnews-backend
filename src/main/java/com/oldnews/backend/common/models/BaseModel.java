package com.oldnews.backend.common.models;

import java.time.LocalDateTime;
import java.util.UUID;

public interface BaseModel {
    public UUID getId();

    public void setId(UUID id);

    public LocalDateTime getCreatedAt();

    public void setCreatedAt(LocalDateTime createdAt);

    public LocalDateTime getUpdatedAt();

    public void setUpdatedAt(LocalDateTime updatedtAt);

    public boolean getDeleted();

    public void setDeleted(boolean deleted);
}
