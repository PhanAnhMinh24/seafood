package com.sales.orders.controller;

import com.sales.orders.pojo.ApiResult;
import com.sales.orders.pojo.request.cart_item.CartItemRequest;
import com.sales.orders.service.cart_item.ICartItemService;
import com.sales.orders.utils.constants.EndpointConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointConstants.CART)
@RequiredArgsConstructor
public class CartController {
    private final ICartItemService cartItemService;

    @GetMapping(EndpointConstants.ID)
    public ResponseEntity<ApiResult<Boolean>> save(@PathVariable Long id) {
        cartItemService.addItem(id);
        return ResponseEntity.ok().body(ApiResult.success(Boolean.TRUE));
    }

    @GetMapping(EndpointConstants.TOTAL_ITEM)
    public ResponseEntity<ApiResult<Long>> totalItemOfCart() {
        return ResponseEntity.ok().body(ApiResult.success(cartItemService.totalItemOfCart()));
    }

    @DeleteMapping(EndpointConstants.ID)
    public ResponseEntity<ApiResult<Boolean>> deleteItem(@PathVariable Long id) {
        cartItemService.deleteItem(id);
        return ResponseEntity.ok().body(ApiResult.success(Boolean.TRUE));
    }
}
