package com.varun.shopping.service.image;

import com.varun.shopping.dto.ImageDto;
import com.varun.shopping.exception.ResourceNotFoundException;
import com.varun.shopping.model.Image;
import com.varun.shopping.model.Product;
import com.varun.shopping.repository.ImageRepository;
import com.varun.shopping.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;

    private final ProductService productRepository;


    @Override
    public Image getImageById(Integer id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + id));
    }

    @Override
    public void deleteImageById(Integer id) {
        try {
            imageRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Image not found with id: " + id);
        }
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Integer productId) {
        Product product = productRepository.getProductById(productId);
        List<ImageDto> savedImageDtos = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);
                // Save image once and update the URL
                Image savedImage = imageRepository.save(image);
                // Update the download URL with the saved image's ID
                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
                // Save only if the ID changes and you need the final correct URL
                imageRepository.save(savedImage);
                savedImageDtos.add(new ImageDto( image.getFileName(), image.getDownloadUrl()));
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDtos;
    }

    @Override
    public ResponseEntity<String> updateImage(MultipartFile file, Integer id) {
        Image image = getImageById(id);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
            return ResponseEntity.ok("Image updated successfully");
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
