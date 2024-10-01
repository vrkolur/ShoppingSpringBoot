package com.varun.shopping.dto;

import com.varun.shopping.model.Category;
import com.varun.shopping.model.Image;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data

public class ProductDto {

    private Integer id;

    private String name;

    private String brand;

    private String description;

    private BigDecimal unitPrice;

    private CategoryDto category;

    private int inventory;

//    private List<ImageDto> images;
}
