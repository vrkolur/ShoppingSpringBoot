package com.varun.shopping.controller;

import com.varun.shopping.dto.ProductDto;
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
        try {
            List<Product> products = productService.getAllProducts();
            List<ProductDto> productDtoList = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Products fetched successfully", productDtoList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Get product by id
    @GetMapping("/search/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Integer id) {
        try {
            Product product = productService.getProductById(id);
            ProductDto productDto = productService.getConvertedProducts(product);
            return ResponseEntity.ok(new ApiResponse("Product fetched successfully", productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    //Get Product by category
    @GetMapping("/search/category/{category}")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            List<ProductDto> productDto = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Products fetched successfully", productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    // Get product by name
    @GetMapping("/search/name/{name}")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
        try {
            List<Product> products = productService.getProductsByName(name);
            List<ProductDto> productDtoList = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Products fetched successfully", productDtoList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    //Get Product by brand
    @GetMapping("/search/brand/{brand}")
    public ResponseEntity<ApiResponse> getProductByBrand(@PathVariable String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            List<ProductDto> productDtoList = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Products fetched successfully", productDtoList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Get product by brand and name
    @GetMapping("/search/{brand}/{name}")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@PathVariable("brand") String brand, @PathVariable("name") String name) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(brand, name);
            List<ProductDto> productDtoList = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Products fetched successfully", productDtoList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Add product
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductRequest productBody) {
        try {
            Product product = productService.addProduct(productBody);
            ProductDto productDto = productService.getConvertedProducts(product);
            return ResponseEntity.ok(new ApiResponse("Product added successfully", productDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Update product
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Integer id, @RequestBody ProductRequest productUpdateBody) {
        try {
            Product product = productService.updateProduct(productUpdateBody, id);
            ProductDto productDto = productService.getConvertedProducts(product);
            return ResponseEntity.ok(new ApiResponse("Product updated successfully", productDto));
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
