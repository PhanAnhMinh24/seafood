package com.sales.products.service.categories;

import com.sales.products.pojo.response.categories.CatagoryResponse;

import java.util.List;

public interface ICategoryService {
    List<CatagoryResponse> getAll();
}
