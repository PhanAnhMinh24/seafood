package com.sales.products.controller;

import com.sales.products.pojo.ApiResult;
import com.sales.products.pojo.request.PagingRequest;
import com.sales.products.pojo.request.products.ProductRequest;
import com.sales.products.pojo.response.products.ProductResponse;
import com.sales.products.service.products.IProductService;
import com.sales.products.utils.constants.CommonConstants;
import com.sales.products.utils.constants.EndpointConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointConstants.PRODUCTS)
@RequiredArgsConstructor
public class ProductControllers {

    private final IProductService productService;

    @GetMapping
    public ResponseEntity<ApiResult<List<ProductResponse>>> getLatestProducts() {
        return ResponseEntity.ok().body(ApiResult.success(productService.getLatestProducts()));
    }

    @GetMapping(EndpointConstants.ID)
    public ResponseEntity<ApiResult<ProductResponse>> getDetail(@PathVariable Long id) {
        return ResponseEntity.ok().body(ApiResult.success(productService.getDetail(id)));
    }

    @PostMapping(EndpointConstants.SEARCH_AND_FILTER)
    public ResponseEntity<ApiResult<Page<ProductResponse>>> searchFilterProducts(@RequestBody PagingRequest<Long> pagingRequest) {
        return ResponseEntity.ok().body(ApiResult.success(productService.searchFilterProducts(pagingRequest)));
    }

    @PostMapping(EndpointConstants.MY_STORE + EndpointConstants.SEARCH_AND_FILTER)
    @PreAuthorize(CommonConstants.ROLE_SELLER)
    public ResponseEntity<ApiResult<Page<ProductResponse>>> searchFilterProductsOfStore(@RequestBody PagingRequest<Long> pagingRequest) {
        return ResponseEntity.ok().body(ApiResult.success(productService.searchFilterProductsOfStore(pagingRequest)));
    }

    @PostMapping()
    @PreAuthorize(CommonConstants.ROLE_SELLER)
    public ResponseEntity<ApiResult<Boolean>> save(@RequestBody ProductRequest productRequest) {
        productService.save(productRequest);
        return ResponseEntity.ok().body(ApiResult.success(Boolean.TRUE));
    }

    @PutMapping()
    @PreAuthorize(CommonConstants.ROLE_SELLER)
    public ResponseEntity<ApiResult<Boolean>> update(@RequestBody ProductRequest productRequest) {
        productService.update(productRequest);
        return ResponseEntity.ok().body(ApiResult.success(Boolean.TRUE));
    }

    @DeleteMapping()
    @PreAuthorize(CommonConstants.ROLE_SELLER)
    public ResponseEntity<ApiResult<Boolean>> delete(@RequestBody List<Long> ids) {
        productService.delete(ids);
        return ResponseEntity.ok().body(ApiResult.success(Boolean.TRUE));
    }
}
