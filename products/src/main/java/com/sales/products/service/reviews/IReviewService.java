package com.sales.products.service.reviews;

import com.sales.products.pojo.request.reviews.ReviewDetailResponse;
import com.sales.products.pojo.request.reviews.ReviewRequest;

public interface IReviewService {
    void save(ReviewRequest reviewRequest);

    ReviewDetailResponse getAllReviews(Long productId);

    void delete(Long reviewId);
}
