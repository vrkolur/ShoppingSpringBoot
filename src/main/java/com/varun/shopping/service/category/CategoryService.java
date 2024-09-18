package com.varun.shopping.service.category;

import com.varun.shopping.dto.CategoryDto;
import com.varun.shopping.exception.AlreadyExistsException;
import com.varun.shopping.exception.ResourceNotFoundException;
import com.varun.shopping.model.Category;
import com.varun.shopping.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Override
    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    @Override
    public Category getCategoryByName(String name) {
        return Optional.ofNullable(categoryRepository.findByName(name))
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with name: " + name));
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) throw new ResourceNotFoundException("Categories not found");
        else return categories;
    }

    @Override
    public Category addCategory(Category category) {
        // Check if the category already exists
        boolean exists = Optional.ofNullable(categoryRepository.findByName(category.getName())).isPresent();
        // Save the category
        if (exists) throw new AlreadyExistsException("Category with name " + category.getName() + " already exists");
        return categoryRepository.save(category);
    }


    @Override
    public Category updateCategory(Integer id, String name) {
        // Only name is allowed to be updated, hence we will do it here only.
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        existingCategory.setName(name);
        return categoryRepository.save(existingCategory);
    }

    @Override
    public ResponseEntity<String> deleteCategoryById(Integer id) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if (existingCategory.isPresent()) {
            categoryRepository.deleteById(id);
            return ResponseEntity.ok("Category deleted successfully");
        } else {
            throw new ResourceNotFoundException("Category not found");
        }

    }

    @Override
    public List<CategoryDto> convertToCategoryDto(List<Category> categories) {
        return categories.stream().map(this::toCategoryDto).toList();
    }

    @Override
    public CategoryDto convertToCategoryDto(Category category) {
        return toCategoryDto(category);
    }

    // PRIVATE METHODS

    private CategoryDto toCategoryDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

}
