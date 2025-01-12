package com.sales.products.service.products;

import com.sales.products.pojo.request.PagingRequest;
import com.sales.products.pojo.request.products.ProductRequest;
import com.sales.products.pojo.response.products.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {
    List<ProductResponse> getLatestProducts();

    ProductResponse getDetail(Long id);

    Page<ProductResponse> searchFilterProducts(PagingRequest<Long> pagingRequest);

    Page<ProductResponse> searchFilterProductsOfStore(PagingRequest<Long> pagingRequest);

    void save(ProductRequest productRequest);

    void update(ProductRequest productRequest);

    void delete(List<Long> ids);
}
