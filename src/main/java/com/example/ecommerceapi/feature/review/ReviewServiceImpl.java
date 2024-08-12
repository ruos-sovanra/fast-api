package com.example.ecommerceapi.feature.review;

import com.example.ecommerceapi.domain.Product;
import com.example.ecommerceapi.domain.Review;
import com.example.ecommerceapi.domain.User;
import com.example.ecommerceapi.feature.product.ProductRepository;
import com.example.ecommerceapi.feature.review.dto.ReviewRequest;
import com.example.ecommerceapi.feature.review.dto.ReviewResponse;
import com.example.ecommerceapi.feature.user.UserRepository;
import com.example.ecommerceapi.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewResponse createReview(ReviewRequest reviewRequest) {
        Review review = reviewMapper.toReview(reviewRequest);
        Product product = productRepository.findById(reviewRequest.productId())
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
        User user = userRepository.findById(reviewRequest.userId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        review.setProduct(product);
        review.setUser(user);
        // Set the userId
        Review savedReview = reviewRepository.save(review); // Save the review and update the reference
        return reviewMapper.toReviewResponse(savedReview); // Use the updated review object
    }


    @Override
    public ReviewResponse updateReview(Long id, ReviewRequest reviewRequest) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Review not found"));
        Product product = productRepository.findById(reviewRequest.productId())
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
        User user = userRepository.findById(reviewRequest.userId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        review.setProduct(product);
        review.setUser(user);
        review.setRating(reviewRequest.rating());
        review.setComment(reviewRequest.comment());
        reviewRepository.save(review);
        return reviewMapper.toReviewResponse(review);
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public List<ReviewResponse> getAllReviewByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findAllByProductId(productId);

        return reviews.stream()
                .map(reviewMapper::toReviewResponse)
                .toList();
    }
}
