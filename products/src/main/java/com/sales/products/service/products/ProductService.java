package com.sales.products.service.products;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sales.products.entity.Categories;
import com.sales.products.entity.Products;
import com.sales.products.entity.QProducts;
import com.sales.products.exception.AppException;
import com.sales.products.exception.ErrorCode;
import com.sales.products.pojo.data.ProductEnum;
import com.sales.products.pojo.request.PagingRequest;
import com.sales.products.pojo.request.products.ProductRequest;
import com.sales.products.pojo.response.products.ProductResponse;
import com.sales.products.repository.ProductRepository;
import com.sales.products.service.BaseService;
import com.sales.products.service.categories.ICategoryService;
import com.sales.products.utils.JwtUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.ExpressionUtils.count;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private final HttpServletRequest request;
    private final JwtUtils jwtUtils;
    private final ICategoryService categoryService;

    @Override
    public List<ProductResponse> getLatestProducts() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QProducts qProducts = QProducts.products;
        return queryFactory.selectFrom(qProducts)
                .where(qProducts.isActive.eq(Boolean.TRUE))
                .orderBy(qProducts.updatedAt.desc())
                .limit(12)
                .fetch()
                .stream()
                .map(this::mapProductResponse)
                .toList();
    }

    @Override
    public ProductResponse getDetail(Long id) {
        Products products = findById(id);
        return mapProductResponse(products);
    }

    @Override
    public Page<ProductResponse> searchFilterProducts(PagingRequest<Long> pagingRequest) {
        Boolean isShowAll = false;
        return executeProductSearch(pagingRequest, null, isShowAll);
    }

    public Page<ProductResponse> searchFilterProductsOfStore(PagingRequest<Long> pagingRequest) {
        Boolean isShowAll = true;
        Long userId = BaseService.getUserId(request, jwtUtils);
        return executeProductSearch(pagingRequest, userId, isShowAll);
    }

    private Page<ProductResponse> executeProductSearch(PagingRequest<Long> pagingRequest, Long userId, Boolean isShowAll) {
        PageRequest pageRequest = BaseService.buildPageRequest(pagingRequest);

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QProducts qProducts = QProducts.products;

        // Build search conditions
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qProducts.category.categoryId.eq(pagingRequest.getFilters()));
        if (isShowAll) {
            builder.and(qProducts.isActive.eq(Boolean.TRUE));
        }
        if (userId != null) {
            builder.and(qProducts.sellerId.eq(userId));
        }

        // Build query
        JPAQuery<Products> query = queryFactory.selectFrom(qProducts)
                .where(builder)
                .orderBy(qProducts.updatedAt.desc());

        // Fetch results
        List<ProductResponse> productResponses = query
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch()
                .stream()
                .map(this::mapProductResponse)
                .toList();

        // Fetch total count
        Long count = queryFactory.select(count(qProducts.productId))
                .from(qProducts)
                .where(builder)
                .fetchOne();
        count = ObjectUtils.defaultIfNull(count, 0L);

        return new PageImpl<>(productResponses, pageRequest, count);
    }

    @Override
    public void save(ProductRequest productRequest) {
        Long userId = BaseService.getUserId(request, jwtUtils);
        Categories categories = categoryService.findByCategory(productRequest.getCategoryId());
        Products products = Products.builder()
                .sellerId(userId)
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .category(categories)
                .imageUrl(productRequest.getImageUrl())
                .sale(productRequest.getSale())
                .status(ProductEnum.available)
                .isActive(productRequest.getIsActive())
                .build();
        productRepository.save(products);
    }

    @Override
    public void update(ProductRequest productRequest) {
        Products products = findById(productRequest.getId());
        products.setName(productRequest.getName());
        products.setDescription(productRequest.getDescription());
        products.setPrice(productRequest.getPrice());
        products.setQuantity(productRequest.getQuantity());
        products.setImageUrl(productRequest.getImageUrl());
        products.setSale(productRequest.getSale());
        products.setStatus(productRequest.getStatus());
        productRepository.save(products);
    }

    @Override
    public void delete(List<Long> ids) {
        for (Long id : ids) {
            Products product = findById(id);
            productRepository.delete(product);
        }
    }

    private Products findById(Long id) {
        Optional<Products> products = productRepository.findById(id);
        if (products.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        return products.get();
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
                .isActive(product.getIsActive())
                .build();
    }
}
