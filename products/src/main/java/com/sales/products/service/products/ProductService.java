package com.sales.products.service.products;

import com.sales.products.pojo.response.products.ProductResponse;
import com.sales.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

    @Override
    public List<ProductResponse> getLatestProducts() {
        return List.of();
    }
}
