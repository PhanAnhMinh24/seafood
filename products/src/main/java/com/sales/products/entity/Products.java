package com.sales.products.entity;

import com.sales.products.pojo.data.ProductEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Products extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    Long productId;

    @Column(name = "seller_id")
    Long sellerId;

    @Column(unique = true, nullable = false)
    String name;

    @Column(columnDefinition = "TEXT")
    String description;

    Float price;

    Integer quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Categories category;

    @Column(columnDefinition = "TEXT", name = "image_url")
    String imageUrl;

    Float sale;

    @Enumerated(EnumType.STRING)
    ProductEnum status;
}
