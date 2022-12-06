package com.oldnews.backend.common.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ImageResponseQueryDTO {
    private List<ImageResponseQueryPageDTO> pages;
}
