package com.example.ecommerceapi.feature.review;

import com.example.ecommerceapi.feature.review.dto.ReviewRequest;
import com.example.ecommerceapi.feature.review.dto.ReviewResponse;

import java.util.List;

public interface ReviewService {
    ReviewResponse createReview(ReviewRequest reviewRequest);
    ReviewResponse updateReview(Long id, ReviewRequest reviewRequest);
    void deleteReview(Long id);
    List<ReviewResponse> getAllReviewByProductId(Long productId);
}
