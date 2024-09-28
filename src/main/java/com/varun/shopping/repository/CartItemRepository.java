package com.varun.shopping.repository;

import com.varun.shopping.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    void deleteAllByCartId(Integer id);

}
