package com.varun.shopping.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {

    private Integer id;

    private String name;

    private String brand;

    private String description;

    private BigDecimal unitPrice;

    private CategoryDto category;

    private int inventory;

}
