package com.varun.shopping.repository;

import com.varun.shopping.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByProductId(Integer productId);
}
