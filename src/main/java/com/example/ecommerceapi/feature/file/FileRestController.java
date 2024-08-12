package com.example.ecommerceapi.feature.file;

import com.example.ecommerceapi.feature.file.dto.FileResponse;
import com.example.ecommerceapi.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileRestController {

    private final FileService fileService;


    @PostMapping(value = "", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<FileResponse> uploadSingleFile(
            @RequestPart("file") MultipartFile file, HttpServletRequest request
    ) {
        return BaseResponse
                .<FileResponse>createSuccess()
                .setPayload(fileService.uploadSingleFile(file, request));
    }


    @PostMapping(value = "/multiple", consumes = "multipart/form-data")
    public BaseResponse<List<String>> uploadMultipleFiles(@RequestPart("files") MultipartFile[] files, HttpServletRequest request) {
        return BaseResponse
                .<List<String>>createSuccess()
                .setPayload(fileService.uploadMultipleFiles(files, request));
    }


    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName, HttpServletRequest request){
        return fileService.serveFile(fileName,request);
    }
    @DeleteMapping("{fileName}")
    public String deleteFile(@PathVariable String fileName) {
        fileService.deleteFile(fileName);
        return "file is deleted successfully!";
    }


}
