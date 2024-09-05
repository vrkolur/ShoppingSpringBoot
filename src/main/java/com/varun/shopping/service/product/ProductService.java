package com.varun.shopping.service.product;

import com.varun.shopping.exception.AlreadyExistsException;
import com.varun.shopping.exception.ResourceNotFoundException;
import com.varun.shopping.model.Category;
import com.varun.shopping.model.Product;
import com.varun.shopping.repository.CategoryRepository;
import com.varun.shopping.repository.ProductRepository;
import com.varun.shopping.request.AddProductRequest;
import com.varun.shopping.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not Found with"));
    }

    @Override
    public Product addProduct(AddProductRequest request) {
        //Check if the Product with same name exists
        List<Product> existingProduct = productRepository.findByName(request.getName());
        if (!existingProduct.isEmpty()) {
            throw new AlreadyExistsException("Product with name " + request.getName() + " already exists");
        }
        // Now Check is the category exists, YES add else create a new one
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));

    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Integer productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Product not Found "));
    }

    @Override
    public ResponseEntity<String> deleteProductById(Integer id) {
        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            productRepository.deleteById(id);
            return ResponseEntity.ok("Product deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    //PRIVATE METHODS

    //creates a new product based on the request and the category passed in
    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getDescription(),
                request.getPrice(),
                category,
                request.getInventory()
        );
    }

    private Product updateExistingProduct(Product existinProduct, ProductUpdateRequest request) {
        existinProduct.setName(request.getName());
        existinProduct.setBrand(request.getBrand());
        existinProduct.setDescription(request.getDescription());
        existinProduct.setPrice(request.getPrice());
        existinProduct.setInventory(request.getInventory());
        Category category = categoryRepository.findByName(request.getCategory().getName());
        existinProduct.setCategory(category);
        return existinProduct;
    }
}
