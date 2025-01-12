package com.sales.authorization.service;

import com.sales.authorization.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;

public class BaseService {
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
}
