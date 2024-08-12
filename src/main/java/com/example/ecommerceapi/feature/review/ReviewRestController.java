package com.example.ecommerceapi.feature.review;

import com.example.ecommerceapi.feature.review.dto.ReviewRequest;
import com.example.ecommerceapi.feature.review.dto.ReviewResponse;
import com.example.ecommerceapi.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewRestController {

    private final ReviewService reviewService;

    @GetMapping("/{productId}")
    @Operation(summary = "Get all reviews by product id")
    public BaseResponse<List<ReviewResponse>> getAllReviewByProductId(@PathVariable Long productId) {
        return BaseResponse.<List<ReviewResponse>>ok()
                .setPayload(reviewService.getAllReviewByProductId(productId));
    }

    @PostMapping
    @Operation(summary = "Create a review")
    public BaseResponse<ReviewResponse> createReview(@Valid @RequestBody ReviewRequest reviewRequest) {
        return BaseResponse.<ReviewResponse>createSuccess()
                .setPayload(reviewService.createReview(reviewRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a review")
    public BaseResponse<ReviewResponse> updateReview(@PathVariable Long id, @RequestBody ReviewRequest reviewRequest) {
        return BaseResponse.<ReviewResponse>updateSuccess()
                .setPayload(reviewService.updateReview(id, reviewRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a review")
    public BaseResponse<String> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return BaseResponse.<String>ok()
                .setPayload("Review deleted successfully");
    }
}
