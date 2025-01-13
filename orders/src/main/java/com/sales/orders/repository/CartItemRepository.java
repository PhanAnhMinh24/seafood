package com.sales.orders.repository;

import com.sales.orders.entity.Cart;
import com.sales.orders.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    long countByCart(Cart cart);

    CartItem findByProductIdAndCart(Long productId, Cart cart);

    List<CartItem> findByCart(Cart cart);
}
