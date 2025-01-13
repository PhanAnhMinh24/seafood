package com.sales.products.service.reviews;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sales.products.entity.Products;
import com.sales.products.entity.QReviews;
import com.sales.products.entity.Reviews;
import com.sales.products.exception.AppException;
import com.sales.products.exception.ErrorCode;
import com.sales.products.pojo.request.reviews.ReviewDetailResponse;
import com.sales.products.pojo.request.reviews.ReviewRequest;
import com.sales.products.pojo.response.reviews.ReviewResponse;
import com.sales.products.pojo.response.users.UserResponse;
import com.sales.products.repository.ReviewRepository;
import com.sales.products.service.ApiService;
import com.sales.products.service.BaseService;
import com.sales.products.service.products.IProductService;
import com.sales.products.utils.JwtUtils;
import com.sales.products.utils.constants.CommonConstants;
import com.sales.products.utils.constants.EndpointConstants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewService implements IReviewService {
    private final ReviewRepository reviewRepository;
    private final HttpServletRequest request;
    private final JwtUtils jwtUtils;
    private final IProductService productService;
    @PersistenceContext
    private EntityManager entityManager;
    private final ApiService apiService;
    @Value("${host.authorization}")
    private String hostAuthorization;

    @Override
    public void save(ReviewRequest reviewRequest) {
        Long userId = BaseService.getUserId(request, jwtUtils);
        Products products = productService.findById(reviewRequest.getProductId());
        Reviews review = Reviews.builder()
                .product(products)
                .userId(userId)
                .rating(reviewRequest.getRating())
                .comment(reviewRequest.getComment())
                .build();
        reviewRepository.save(review);
    }

    @Override
    public ReviewDetailResponse getAllReviews(Long productId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QReviews qReviews = QReviews.reviews;
        List<ReviewResponse> reviewResponses = queryFactory.selectFrom(qReviews)
                .where(qReviews.product.productId.eq(productId))
                .orderBy(qReviews.updatedAt.desc())
                .fetch()
                .stream()
                .map(this::mapReviewResponse)
                .toList();
        List<Long> userIds = reviewResponses.stream().map(ReviewResponse::getUserId).toList();
        List<UserResponse> userResponses = getProfile(userIds);
        long countRating = reviewResponses.stream().mapToLong(ReviewResponse::getRating).sum();
        double averageRating = 0;
        if (!reviewResponses.isEmpty()) {
            averageRating = (double) (countRating / reviewResponses.size());
        }
        List<ReviewResponse> mapReviewResponses = reviewResponses.stream()
                .peek(item -> {
                    UserResponse userResponse = userResponses.stream()
                            .filter(tmp -> tmp.getId().equals(item.getUserId()))
                            .findFirst()
                            .orElse(null);
                    String fullName = ObjectUtils.isEmpty(userResponse) ? CommonConstants.EMPTY : userResponse.getFullName();
                    item.setFullName(fullName);
                })
                .toList();
        return ReviewDetailResponse.builder()
                .averageRating(averageRating)
                .reviewResponses(mapReviewResponses)
                .build();
    }

    private List<UserResponse> getProfile(List<Long> userIds) {
        return apiService.callApiPost(hostAuthorization.concat(EndpointConstants.USER_PROFILE), userIds,
                BaseService.getToken(request), UserResponse.class);
    }

    @Override
    public void delete(Long reviewId) {
        Reviews review = findById(reviewId);
        Long userId = BaseService.getUserId(request, jwtUtils);
        if (!review.getUserId().equals(userId)) {
            throw new AppException(ErrorCode.REVIEW_NOT_YOURS);
        }
        reviewRepository.delete(review);
    }

    private Reviews findById(Long reviewId) {
        Optional<Reviews> review = reviewRepository.findById(reviewId);
        if (review.isEmpty()) {
            throw new AppException(ErrorCode.REVIEW_NOT_FOUND);
        }
        return review.get();
    }

    private ReviewResponse mapReviewResponse(Reviews review) {
        return ReviewResponse.builder()
                .id(review.getReviewId())
                .userId(review.getUserId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .updateAt(review.getUpdatedAt())
                .build();
    }
}
