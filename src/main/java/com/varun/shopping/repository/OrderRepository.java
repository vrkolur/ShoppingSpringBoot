package com.varun.shopping.repository;

import com.varun.shopping.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserId(Integer userId);

}
