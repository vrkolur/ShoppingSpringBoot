package com.varun.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageDto {
    private Integer id;

    private String name;

    private String downloadUrl;
}
