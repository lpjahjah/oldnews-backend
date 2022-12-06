package com.oldnews.backend.common.dtos;

import lombok.Data;

@Data
public class ImageResponseQueryPageDTO {
    private String title;

    private ImageResponseQueryPageImageDTO original;
}
