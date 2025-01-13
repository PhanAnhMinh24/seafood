package com.sales.orders.service.cart_item;

import com.sales.orders.entity.Cart;
import com.sales.orders.entity.CartItem;
import com.sales.orders.exception.AppException;
import com.sales.orders.exception.ErrorCode;
import com.sales.orders.pojo.response.cart_item.CartItemResponse;
import com.sales.orders.repository.CartItemRepository;
import com.sales.orders.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final ICartService cartService;

    @Override
    public void addItem(Long productId) {
        Cart cart = cartService.create();
        CartItem cartItem = cartItemRepository.findByProductIdAndCart(productId, cart);
        if (ObjectUtils.isEmpty(cartItem)) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProductId(productId);
            cartItem.setQuantity(1);
        } else {
            int quantity = cartItem.getQuantity() + 1;
            cartItem.setQuantity(quantity);
        }
        cartItemRepository.save(cartItem);
    }

    @Override
    public long totalItemOfCart() {
        Cart cart = cartService.create();
        return cartItemRepository.countByCart(cart);
    }

    @Override
    public void deleteItem(Long cartItemId) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isEmpty()) {
            throw new AppException(ErrorCode.SYSTEM_ERROR);
        }
        CartItem cartItem = cartItemOptional.get();
        cartItemRepository.delete(cartItem);
    }

    @Override
    public List<CartItemResponse> getAll() {
        Cart cart = cartService.create();
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        return List.of();
    }


}
