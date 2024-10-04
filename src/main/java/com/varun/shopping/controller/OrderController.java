package com.varun.shopping.controller;

import com.varun.shopping.dto.OrderDto;
import com.varun.shopping.exception.ResourceNotFoundException;
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
        OrderDto orderDto = null;
        try {
            orderDto = orderService.placeOrder(userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.ok(new ApiResponse("Order created successfully", orderDto));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Integer orderId) {
        OrderDto orderDto = null;
        try {
            orderDto = orderService.getOrderById(orderId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.ok(new ApiResponse("Order fetched successfully", orderId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Integer userId) {
        List<OrderDto> orderDtoList = null;
        try {
            orderDtoList = orderService.getUserOrders(userId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.ok(new ApiResponse("Orders fetched successfully", orderDtoList));
    }

}
