package com.varun.shopping.controller;

import com.varun.shopping.exception.ResourceNotFoundException;
import com.varun.shopping.model.Order;
import com.varun.shopping.response.ApiResponse;
import com.varun.shopping.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createOrder(Integer userId) {
        Order order = null;
        try {
            order = orderService.placeOrder(userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.ok(new ApiResponse("Order created successfully", order));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Integer orderId) {
        Order order = null;
        try {
            order = orderService.getOrderById(orderId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.ok(new ApiResponse("Order fetched successfully", order));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Integer userId) {
        List<Order> orders = null;
        try {
            orders = orderService.getUserOrders(userId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.ok(new ApiResponse("Orders fetched successfully", orders));
    }

}
