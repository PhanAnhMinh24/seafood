package com.sales.authorization.controller;

import com.sales.authorization.pojo.request.LoginRequest;
import com.sales.authorization.pojo.request.SignupRequest;
import com.sales.authorization.pojo.response.JwtResponse;
import com.sales.authorization.service.auth.IAuthService;
import com.sales.authorization.utils.ErrorCode;
import com.sales.authorization.utils.JwtUtils;
import com.sales.authorization.utils.constants.EndpointConstants;
import com.sales.exception.exception.AppException;
import com.sales.generic.pojo.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = EndpointConstants.AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;
    private final JwtUtils jwtUtils;

    @PostMapping(value = EndpointConstants.SIGN_IN)
    public ResponseEntity<ApiResult<JwtResponse>> authenticateUser(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.login(loginRequest);
        return ResponseEntity.ok().body(ApiResult.success(jwtResponse));
    }

    @PostMapping(value = EndpointConstants.SIGN_UP)
    public ResponseEntity<ApiResult<Boolean>> registerUser(@RequestBody SignupRequest signupRequest) {
        try {
            authService.registerUser(signupRequest);
            return ResponseEntity.ok().body(ApiResult.success(Boolean.TRUE));
        } catch (Exception e) {
            throw new AppException(ErrorCode.DURING_REGISTRATION_ERROR);
        }
    }

}
