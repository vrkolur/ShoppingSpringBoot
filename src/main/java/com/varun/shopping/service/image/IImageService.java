package com.varun.shopping.service.image;

import com.varun.shopping.model.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    Image getImageById(Integer id);

    void deleteImageById(Integer id);

    ResponseEntity<String> saveImages(List<MultipartFile> file, Integer id);

    ResponseEntity<String> updateImage(MultipartFile file, Integer id);
}
