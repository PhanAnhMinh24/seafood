package com.sales.products.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    // Generate JWT token from username
    private String buildToken(Map<String, Object> extraClaims, String username) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)  // Đặt username làm subject
                .setIssuedAt(new Date())  // Đặt thời gian phát hành token
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))  // Đặt thời gian hết hạn
                .signWith(key(), SignatureAlgorithm.HS256)  // Dùng thuật toán HS512 và key bí mật để ký token
                .compact();  // Trả về token đã được tạo
    }

    public String generateToken(String username) {
        return buildToken(new HashMap<>(), username);
    }

    public String generateToken(Long id, String username, List<String> roles) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("roles", roles);
        extraClaims.put("user_id", id);
        return buildToken(extraClaims, username);
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Validate the JWT token
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()  // Sử dụng parser() thay vì parserBuilder()
                    .setSigningKey(jwtSecret)  // Đặt key bí mật để xác thực token
                    .build()
                    .parseClaimsJws(authToken);  // Kiểm tra xem token có hợp lệ không
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;  // Nếu gặp lỗi thì token không hợp lệ
    }

    // Get the username from JWT token
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()  // Sử dụng parser() thay vì parserBuilder()
                .setSigningKey(jwtSecret)  // Đặt key bí mật để xác thực token
                .build()
                .parseClaimsJws(token)  // Giải mã token
                .getBody()  // Lấy payload (phần body)
                .getSubject();  // Trả về username
    }

    public List<String> getRoles(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("roles", List.class);
    }

    public Long getUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("user_id", Long.class);
    }
}
