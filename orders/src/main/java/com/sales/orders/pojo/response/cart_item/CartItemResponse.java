package com.sales.orders.pojo.response.cart_item;

import com.sales.orders.pojo.response.product.ProductResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    Long cartItemId;
    Integer quantity;
    Long productId;
    ProductResponse productResponse;
}
