package com.sales.products.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Reviews extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    Long reviewId;

    @Column(name = "user_id")
    Long userId;

    Integer rating;

    @Column(columnDefinition = "TEXT")
    String comment;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Products product;
}
