package com.varun.shopping.service.product;

import com.varun.shopping.dto.ImageDto;
import com.varun.shopping.dto.ProductDto;
import com.varun.shopping.exception.AlreadyExistsException;
import com.varun.shopping.exception.ResourceNotFoundException;
import com.varun.shopping.model.Category;
import com.varun.shopping.model.Image;
import com.varun.shopping.model.Product;
import com.varun.shopping.repository.CategoryRepository;
import com.varun.shopping.repository.ImageRepository;
import com.varun.shopping.repository.ProductRepository;
import com.varun.shopping.request.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    private final ImageRepository imageRepository;

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not Found with"));
    }

    @Override
    public Product addProduct(ProductRequest request) {
        //Check if the Product with same name exists
        List<Product> existingProduct = productRepository.findByName(request.getName());
        if (!existingProduct.isEmpty()) {
            throw new AlreadyExistsException("Product with name " + request.getName() + " already exists");
        }
        // Now Check is the category exists, YES then add else create a new category and then save
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }


    @Override
    public Product updateProduct(ProductRequest request, Integer productId) {
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
            throw new ResourceNotFoundException("Product not found");
        }
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty())
            throw new ResourceNotFoundException("There are no Products");
        else
            return products;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategoryName(category);
        if (products.isEmpty())
            throw new ResourceNotFoundException("There are no Products in " + category);
        else
            return products;
    }

    @Override
    public List<Product> getProductsByName(String name) {
        List<Product> products = productRepository.findByName(name);
        if (products.isEmpty())
            throw new ResourceNotFoundException("There are no Products with name " + name);
        else
            return products;
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        List<Product> products = productRepository.findByBrand(brand);
        if (products.isEmpty())
            throw new ResourceNotFoundException("There are no Products with brand " + brand);
        else
            return products;
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        List<Product> products = productRepository.findByCategoryNameAndBrand(category, brand);
        if (products.isEmpty())
            throw new ResourceNotFoundException("There are no Products with category " + category + " and brand " + brand);
        else
            return products;
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToProductDto).toList();
    }

    @Override
    public ProductDto getConvertedProducts(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    //PRIVATE METHODS

    // Creates a new product based on the request and the category passed in
    private Product createProduct(ProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getDescription(),
                request.getUnitPrice(),
                category,
                request.getInventory()
        );
    }

    // Update Existing product base on the request provided.
    private Product updateExistingProduct(Product existinProduct, ProductRequest request) {
        existinProduct.setName(request.getName());
        existinProduct.setBrand(request.getBrand());
        existinProduct.setDescription(request.getDescription());
        existinProduct.setUnitPrice(request.getUnitPrice());
        existinProduct.setInventory(request.getInventory());
        Category category = categoryRepository.findByName(request.getCategory().getName());
        existinProduct.setCategory(category);
        return existinProduct;
    }

    // Convert Product to ProductDto
    private ProductDto convertToProductDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
//        List<Image> images = imageRepository.findByProductId(product.getId());
//        List<ImageDto> imageDtos = images.stream()
//                .map(image -> modelMapper.map(image, ImageDto.class)).toList();
//        productDto.setImages(imageDtos);
        return productDto;
    }


}
