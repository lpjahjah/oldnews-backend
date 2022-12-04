package com.oldnews.backend.app.dtos;

import lombok.Data;


@Data
public class ExceptionResponseDto {

    public ExceptionResponseDto(String title, String description, String endpoint) {
        this.exception = new ExceptionResponseDtoAttributes(
                title,
                description,
                endpoint
        );
    }

    private ExceptionResponseDtoAttributes exception;
}
