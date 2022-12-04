package com.oldnews.backend.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseDtoAttributes {
    private String title;

    private String description;

    private String endpoint;
}
