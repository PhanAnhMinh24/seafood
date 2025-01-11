package com.sales.products.service.products;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sales.products.entity.Products;
import com.sales.products.entity.QProducts;
import com.sales.products.pojo.response.products.ProductResponse;
import com.sales.products.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProductResponse> getLatestProducts() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QProducts qProducts = QProducts.products;
        return queryFactory.selectFrom(qProducts)
                .orderBy(qProducts.createdAt.desc())
                .limit(12)
                .fetch()
                .stream()
                .map(this::mapProductResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> getProducts(Long categoryId) {
        return List.of();
    }

    private ProductResponse mapProductResponse(Products product) {
        return ProductResponse.builder()
                .id(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .imageUrl(product.getImageUrl())
                .sale(product.getSale())
                .status(product.getStatus())
                .build();
    }
}
