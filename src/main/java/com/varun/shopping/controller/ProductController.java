package com.varun.shopping.controller;

import com.varun.shopping.exception.AlreadyExistsException;
import com.varun.shopping.exception.ResourceNotFoundException;
import com.varun.shopping.model.Product;
import com.varun.shopping.request.ProductRequest;
import com.varun.shopping.response.ApiResponse;
import com.varun.shopping.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;

    // Get all products
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(new ApiResponse("Products fetched successfully", products));
    }

    // Get product by id
    @GetMapping("/search/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Integer id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse("Product fetched successfully", product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    //Get Product by category
    @GetMapping("/search/category/{category}")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            return ResponseEntity.ok(new ApiResponse("Products fetched successfully", products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Get product by name
    @GetMapping("/search/name/{name}")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
        try {
            List<Product> products = productService.getProductsByName(name);
            return ResponseEntity.ok(new ApiResponse("Products fetched successfully", products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    //Get Product by brand
    @GetMapping("/search/brand/{brand}")
    public ResponseEntity<ApiResponse> getProductByBrand(@PathVariable String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            return ResponseEntity.ok(new ApiResponse("Products fetched successfully", products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Get product by brand and name
    // Try making the PathVariable as RequestParams
    @GetMapping("/search/{brand}/{name}")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@PathVariable("brand") String brand, @PathVariable("name") String name) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(brand, name);
            return ResponseEntity.ok(new ApiResponse("Products fetched successfully", products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Add product
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductRequest productBody) {
        try {
            Product product = productService.addProduct(productBody);
            return ResponseEntity.ok(new ApiResponse("Product added successfully", product));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Update product
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Integer id, @RequestBody ProductRequest productUpdateBody) {
        try {
            Product product = productService.updateProduct(productUpdateBody, id);
            return ResponseEntity.ok(new ApiResponse("Product updated successfully", product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Delete product
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteProductById(@PathVariable Integer id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("Product deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


}
