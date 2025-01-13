package com.sales.orders.service;

import com.sales.orders.pojo.request.PagingRequest;
import com.sales.orders.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class BaseService {
    public static PageRequest buildPageRequest(PagingRequest pagingRequest) {
        int page = pagingRequest.getPage() > 0 ? pagingRequest.getPage() - 1 : 0;
        Sort.Direction sortDirection = pagingRequest.getSortBy() != null ? pagingRequest.getSortBy() : Sort.Direction.ASC;
        return StringUtils.isBlank(pagingRequest.getSortKey())
                ? PageRequest.of(page, pagingRequest.getSize())
                : PageRequest.of(page, pagingRequest.getSize(), Sort.by(sortDirection, pagingRequest.getSortKey()));
    }

    public static Long getUserId(HttpServletRequest request, JwtUtils jwtUtils) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header is missing or invalid");
        }

        // Tách token từ chuỗi header
        String token = authorizationHeader.substring(7);

        // Giải mã token (nếu sử dụng JWT) để lấy userId
        return jwtUtils.getUserId(token);
    }

    public static String getToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header is missing or invalid");
        }

        // Tách token từ chuỗi header
        return authorizationHeader.substring(7);
    }
}
