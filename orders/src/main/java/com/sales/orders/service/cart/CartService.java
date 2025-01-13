package com.sales.orders.service.cart;

import com.sales.orders.entity.Cart;
import com.sales.orders.repository.CartRepository;
import com.sales.orders.service.BaseService;
import com.sales.orders.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final HttpServletRequest request;
    private final JwtUtils jwtUtils;

    @Override
    public Cart create() {
        Long userId = BaseService.getUserId(request, jwtUtils);
        Cart cart = cartRepository.findByBuyerId(userId);
        if (ObjectUtils.isEmpty(cart)) {
            cart = new Cart();
            cart.setBuyerId(userId);
            return cartRepository.save(cart);
        }
        return cart;
    }
}
