package com.example.ecommerceapi.adviser;

import com.example.ecommerceapi.utils.BaseResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalRestControllerAdviser {
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseResponse<?> handleNoSuchElementException(NoSuchElementException ex) {
        return BaseResponse
                .notFound()
                .setMetadata(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST);

        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public BaseResponse<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        String message = e.getMostSpecificCause().getMessage();
        if (message.contains("email")) {
            return BaseResponse.badRequest()
                    .setMetadata("Email already exists");
        } else if (message.contains("user_name")) {
            return BaseResponse.badRequest()
                    .setMetadata("Username already exists");
        }else {
            // Handle other types of DataIntegrityViolationException or return a generic message
            return BaseResponse.badRequest()
                    .setMetadata("Data integrity violation");
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return BaseResponse
                .badRequest()
                .setMetadata(ex.getMessage());
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
//        Map<String, Object> body = new HashMap<>();
//        body.put("status", HttpStatus.BAD_REQUEST);
//        body.put("errors", ex.getBindingResult().getAllErrors());
//
//        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
//    }

    //    image exception , image size, image format ...
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException e) {
        return new ResponseEntity<>(e.getReason(), e.getStatusCode());
    }

}
