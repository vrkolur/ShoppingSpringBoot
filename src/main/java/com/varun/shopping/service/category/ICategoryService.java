package com.varun.shopping.service.category;

import com.varun.shopping.dto.CategoryDto;
import com.varun.shopping.model.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICategoryService {

    Category getCategoryById(Integer id);

    Category getCategoryByName(String name);

    List<Category> getAllCategories();

    Category addCategory(Category category);

    Category updateCategory(Integer id, String name);

    ResponseEntity<String> deleteCategoryById(Integer id);

    CategoryDto convertToCategoryDto(Category category);

    List<CategoryDto> convertToCategoryDto(List<Category> categories);

}
