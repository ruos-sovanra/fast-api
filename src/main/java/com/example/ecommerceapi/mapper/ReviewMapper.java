package com.example.ecommerceapi.mapper;

import com.example.ecommerceapi.domain.Review;
import com.example.ecommerceapi.feature.review.dto.ReviewRequest;
import com.example.ecommerceapi.feature.review.dto.ReviewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    // ReviewResponse toReviewResponse(Review review);
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "userName", source = "user.userName")
    ReviewResponse toReviewResponse(Review review);
    // Review toReview(ReviewRequest reviewRequest);
    Review toReview(ReviewRequest reviewRequest);
}
