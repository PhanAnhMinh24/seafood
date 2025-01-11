package com.sales.products.controller;

import com.sales.products.pojo.ApiResult;
import com.sales.products.pojo.response.categories.CatagoryResponse;
import com.sales.products.service.categories.ICategoryService;
import com.sales.products.utils.constants.EndpointConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = EndpointConstants.CATEGORIES)
@RequiredArgsConstructor
public class CategoriesController {
    private final ICategoryService catagoryService;

    @GetMapping
    public ResponseEntity<ApiResult<List<CatagoryResponse>>> getAll() {
        return ResponseEntity.ok().body(ApiResult.success(catagoryService.getAll()));
    }
}
