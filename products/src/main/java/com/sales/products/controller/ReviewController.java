package com.sales.products.controller;

import com.sales.products.pojo.ApiResult;
import com.sales.products.pojo.request.reviews.ReviewDetailResponse;
import com.sales.products.pojo.request.reviews.ReviewRequest;
import com.sales.products.service.reviews.IReviewService;
import com.sales.products.utils.constants.EndpointConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointConstants.REVIEWS)
@RequiredArgsConstructor
public class ReviewController {
    private final IReviewService reviewService;

    @GetMapping(EndpointConstants.ID)
    public ResponseEntity<ApiResult<ReviewDetailResponse>> getAllReviews(@PathVariable Long id) {
        return ResponseEntity.ok().body(ApiResult.success(reviewService.getAllReviews(id)));
    }

    @PostMapping()
    public ResponseEntity<ApiResult<Boolean>> save(@RequestBody ReviewRequest reviewRequest) {
        reviewService.save(reviewRequest);
        return ResponseEntity.ok().body(ApiResult.success(Boolean.TRUE));
    }

    @DeleteMapping(EndpointConstants.ID)
    public ResponseEntity<ApiResult<Boolean>> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.ok().body(ApiResult.success(Boolean.TRUE));
    }
}
