package com.sales.products.controller;

import com.sales.products.pojo.ApiResult;
import com.sales.products.pojo.response.products.ProductResponse;
import com.sales.products.service.products.IProductService;
import com.sales.products.utils.constants.EndpointConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = EndpointConstants.PRODUCTS)
@RequiredArgsConstructor
public class ProductControllers {

    private final IProductService productService;

    @GetMapping
    public ResponseEntity<ApiResult<List<ProductResponse>>> getLatestProducts() {
        return ResponseEntity.ok().body(ApiResult.success(productService.getLatestProducts()));
    }
}
