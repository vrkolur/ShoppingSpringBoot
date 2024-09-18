package com.varun.shopping.repository;

import com.varun.shopping.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findByName(String name);

}
