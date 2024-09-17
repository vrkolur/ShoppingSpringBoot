package com.varun.shopping.controller;

import com.varun.shopping.dto.ImageDto;
import com.varun.shopping.exception.ResourceNotFoundException;
import com.varun.shopping.model.Image;
import com.varun.shopping.response.ApiResponse;
import com.varun.shopping.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {

    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(
            @RequestParam List<MultipartFile> files,
            @RequestParam Integer productId
    ) {
        List<ImageDto> imageDtos = imageService.saveImages(files, productId);
        try {
            return ResponseEntity.ok(new ApiResponse("Images saved successfully", imageDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Something went wrong: ", e.getMessage()));
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Integer id) {
        Image image = imageService.getImageById(id);
        try {
            // Ensure proper byte array retrieval
            ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));

            // Set up the response entity with proper headers and content type
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(image.getFileType()))  // Ensure media type matches file type
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Integer id, @RequestBody MultipartFile file) {
        Image image = imageService.getImageById(id);
        try {
            if (image != null) {
                imageService.updateImage(file, id);
                return ResponseEntity.ok(new ApiResponse("Image updated successfully ", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Something went wrong ", null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Integer id) {
        try {
            imageService.deleteImageById(id);
            return ResponseEntity.ok(new ApiResponse("Image deleted successfully ", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


}
