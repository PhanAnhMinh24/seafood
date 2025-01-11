package com.sales.products.service.products;

import com.sales.products.pojo.response.products.ProductResponse;
import com.sales.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

    @Override
    public List<ProductResponse> getLatestProducts() {
//        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
//        Pageable pageable = PageRequest.of(0, 12, sort);
        return productRepository.findAll().stream()
                .map(item->ProductResponse.builder()
                        .id(item.getProductId())
                        .name(item.getName())
                        .description(item.getDescription())
                        .price(item.getPrice())
                        .quantity(item.getQuantity())
                        .imageUrl(item.getImageUrl())
                        .sale(item.getSale())
                        .status(item.getStatus())
                        .build())
                .toList();
    }
}
