package com.sales.orders.service.cart_item;

import com.sales.orders.pojo.response.cart_item.CartItemResponse;

import java.util.List;

public interface ICartItemService {
    void addItem(Long productId);

    long totalItemOfCart();

    void deleteItem(Long cartItemId);

    List<CartItemResponse> getAll();
}
