package com.varun.shopping.request;


import com.varun.shopping.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {

    private String name;

    private String brand;

    private String description;

    private BigDecimal unitPrice;

    private Category category;

    private int inventory;
}
