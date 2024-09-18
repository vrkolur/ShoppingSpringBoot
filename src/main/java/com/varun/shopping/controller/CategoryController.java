package com.varun.shopping.controller;


import com.varun.shopping.dto.CategoryDto;
import com.varun.shopping.exception.AlreadyExistsException;
import com.varun.shopping.exception.ResourceNotFoundException;
import com.varun.shopping.model.Category;
import com.varun.shopping.response.ApiResponse;
import com.varun.shopping.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    // Get all categories
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<CategoryDto> categoryDtosList = categoryService.convertToCategoryDto(categoryService.getAllCategories());
            return ResponseEntity.ok(new ApiResponse("Categories fetched successfully", categoryDtosList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Get category by id
    @GetMapping("/search/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@RequestBody Integer id) {
        try {
            CategoryDto categoryDtoList = categoryService.convertToCategoryDto(categoryService.getCategoryById(id));
            return ResponseEntity.ok(new ApiResponse("Category fetched successfully", categoryDtoList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Get category by name
    @GetMapping("/search/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@RequestBody String name) {
        try {
            CategoryDto categoryDto = categoryService.convertToCategoryDto(categoryService.getCategoryByName(name));
            return ResponseEntity.ok(new ApiResponse("Category fetched successfully", categoryDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Add category
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category) {
        try {
            CategoryDto categoryDto = categoryService.convertToCategoryDto(categoryService.addCategory(category));
            return ResponseEntity.ok(new ApiResponse("Category added successfully", categoryDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Delete category
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id) {
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Category deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Update category
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Integer id, @RequestBody String name) {
        try {
            CategoryDto updatedCategoryDto = categoryService.convertToCategoryDto(categoryService.updateCategory(id, name));
            return ResponseEntity.ok(new ApiResponse("Category updated successfully", updatedCategoryDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
