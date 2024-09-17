package com.varun.shopping.service.category;

import com.varun.shopping.exception.AlreadyExistsException;
import com.varun.shopping.exception.ResourceNotFoundException;
import com.varun.shopping.model.Category;
import com.varun.shopping.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not Found with"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        // Check if the category already exists
        // Save the category
        return Optional.of(category)
                .map(categoryRepository::save)
                .orElseThrow(() -> new AlreadyExistsException("Category already exists with name: " + category.getName()));

    }

    @Override
    public Category updateCategory(Category category, Integer id) {
        // Only name is allowed to be updated, hence we will do it here only.
        return Optional.ofNullable(getCategoryById(id))
                .map(existingCategory -> {
                    existingCategory.setName(category.getName());
                    return categoryRepository.save(existingCategory);
                }).orElseThrow(() -> new ResourceNotFoundException("Category not Found with"));
    }

    @Override
    public ResponseEntity<String> deleteCategoryById(Integer id) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if (existingCategory.isPresent()) {
            categoryRepository.deleteById(id);
            return ResponseEntity.ok("Category deleted successfully");
        }else{
            throw new ResourceNotFoundException("Category not found");
        }

    }

}
