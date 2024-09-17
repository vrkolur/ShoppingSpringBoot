package com.varun.shopping.service.product;

import com.varun.shopping.dto.ProductDto;
import com.varun.shopping.model.Product;
import com.varun.shopping.request.ProductRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProductService {

    Product addProduct(ProductRequest product);

    ResponseEntity<String> deleteProductById(Integer id);

    Product updateProduct(ProductRequest product, Integer id);

    Product getProductById(Integer id);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    ProductDto getConvertedProducts(Product product);

    List<ProductDto> getConvertedProducts(List<Product> products);

}
