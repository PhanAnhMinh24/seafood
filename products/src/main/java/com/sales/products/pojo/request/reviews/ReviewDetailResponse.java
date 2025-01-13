package com.sales.products.pojo.request.reviews;

import com.sales.products.pojo.response.reviews.ReviewResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewDetailResponse {
    Double averageRating;
    List<ReviewResponse> reviewResponses;
}
