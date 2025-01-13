package com.sales.products.pojo.response.reviews;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewResponse {
    Long id;
    Long userId;
    String fullName;
    Integer rating;
    String comment;
    LocalDateTime createdAt;
    LocalDateTime updateAt;
}
