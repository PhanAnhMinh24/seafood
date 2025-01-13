package com.sales.orders.pojo.request.cart_item;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemRequest {
    Long productId;
    Integer quantity;
}
