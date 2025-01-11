package com.sales.products.service.products;

import com.sales.products.pojo.response.products.ProductResponse;

import java.util.List;

public interface IProductService {
    List<ProductResponse> getLatestProducts();
}
