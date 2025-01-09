package com.sales.authorization.service.auth;

import com.sales.authorization.entity.User;
import com.sales.authorization.pojo.request.LoginRequest;
import com.sales.authorization.pojo.request.SignupRequest;
import com.sales.authorization.pojo.response.JwtResponse;

import java.util.List;

public interface IAuthService {
    User registerUser(SignupRequest signupRequest);

    JwtResponse login(LoginRequest loginRequest);
}
