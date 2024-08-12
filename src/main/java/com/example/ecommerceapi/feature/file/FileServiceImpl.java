package com.example.ecommerceapi.feature.file;

import com.example.ecommerceapi.feature.file.dto.FileResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class FileServiceImpl implements FileService {
    @Value("${file_storage.image_location}")
    String fileStorageDir;
    private static final Set<String> SUPPORTED_IMAGE_TYPES = Set.of(
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_GIF_VALUE);

    private String generateImageUrl(HttpServletRequest request, String filename) {
        return String.format("%s://%s:%d/images/%s",
                request.getScheme(),
                request.getServerName(),
                request.getServerPort(),
                filename);
    }

    private String generateDownloadImageUrl(HttpServletRequest request, String filename) {
        return String.format("%s://%s:%d/api/v1/files/download/%s",
                request.getScheme(),
                request.getServerName(),
                request.getServerPort(),
                filename);
    }

    private String uploadFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (!SUPPORTED_IMAGE_TYPES.contains(contentType)) {
            throw new ResponseStatusException(
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                    contentType + " not allowed!!");
        }
        try {
            Path fileStoragePath = Path.of(fileStorageDir);
            if (!Files.exists(fileStoragePath)) {
                try {
                    Files.createDirectories(fileStoragePath);
                } catch (AccessDeniedException e) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied: " + e.getMessage());
                }
            }
            String fileName = UUID.randomUUID() + "." +
                    Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];

            Files.copy(file.getInputStream(),
                    fileStoragePath.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public FileResponse uploadSingleFile(MultipartFile file, HttpServletRequest request) {
        String filename = uploadFile(file);
        String fullImageUrl = generateImageUrl(request, filename);
        return FileResponse.builder()
                .downloadUrl(generateDownloadImageUrl(request, filename))
                .fileType(file.getContentType())
                .size((float) file.getSize() / 1024) // in KB
                .filename(filename)
                .fullUrl(fullImageUrl).build();
    }

    @Override
    public List<String> uploadMultipleFiles(MultipartFile[] files, HttpServletRequest request) {
        var fileUrls = new ArrayList<String>();
        for (var file : files) {
            FileResponse fileResponse = uploadSingleFile(file, request);
            fileUrls.add(fileResponse.fullUrl());
        }
        return fileUrls;
    }

    @Override
    public ResponseEntity<Resource> serveFile(String filename, HttpServletRequest request) {
        try {
            Path imagePath = Path.of(fileStorageDir).resolve(filename);
            Resource resourceUrl = new UrlResource(imagePath.toUri());
            if (resourceUrl.exists()) {
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.parseMediaType("image/jpeg"))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resourceUrl.getFilename() + "\"")
                        .body(resourceUrl);
            } else {
                throw new RuntimeException("Resources not found!");
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteFile(String filename) {
        // Implementation for deleting a file
    }
}