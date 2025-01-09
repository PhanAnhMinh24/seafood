package com.sales.authorization.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sales.authorization.service.user.UserDetailsServiceImpl;
import com.sales.authorization.utils.ErrorCode;
import com.sales.authorization.utils.JwtUtils;
import com.sales.authorization.utils.constants.CommonConstants;
import com.sales.authorization.utils.constants.EndpointConstants;
import com.sales.exception.exception.AppException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Log4j2
@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            String headerStr = request.getHeader("Authorization");

            if (org.apache.commons.lang3.StringUtils.isBlank(headerStr)) {
                setErrorMessage(response, new AppException(ErrorCode.UNAUTHORIZED));
                return;
            }

            String jwt = parseJwt(request);
            if (org.apache.commons.lang3.StringUtils.isBlank(jwt)) {
                setErrorMessage(response, new AppException(ErrorCode.UNAUTHORIZED));
                return;
            }
            if (jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                filterChain.doFilter(request, response);
            }
        } catch (AppException e) {
            logger.error("Cannot set user authentication: {}", e);
            setErrorMessage(response, e);
        }
    }

    private void setErrorMessage(HttpServletResponse response, AppException e) throws IOException {
        response.setStatus(e.getErrorCode().getStatus().value());
        response.getWriter().write(objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(Map.of(
                        CommonConstants.MESSAGE, e.getErrorCode().getMessage(),
                        CommonConstants.STATUS, e.getErrorCode().getStatus()
                )));
        response.setHeader(CommonConstants.CONTENT_TYPE, CommonConstants.APPLICATION_JSON_UTF8);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        String[] jwtExcludeEndpoints = {
                EndpointConstants.AUTH + EndpointConstants.SIGN_IN,
                EndpointConstants.AUTH + EndpointConstants.SIGN_UP
        };

        return Arrays.stream(jwtExcludeEndpoints).anyMatch(path::startsWith);
    }
}
