package com.sales.orders.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_item")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CartItem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    Long cartItemId;

    @Column(name = "product_id")
    Long productId;

    Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    Cart cart;
}
