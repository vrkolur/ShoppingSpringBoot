package com.varun.shopping.request;

import com.varun.shopping.model.Category;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductUpdateRequest {
    private String name;

    private String brand;

    private String description;

    private BigDecimal price;

    private Category category;

    private int inventory;
}
