package com.sales.authorization.service.auth;

import com.sales.authorization.pojo.request.LoginRequest;
import com.sales.authorization.pojo.request.SignupRequest;
import com.sales.authorization.pojo.response.JwtResponse;
import com.sales.generic.entity.User;

public interface IAuthService {
    User registerUser(SignupRequest signupRequest);

    JwtResponse login(LoginRequest loginRequest);
}
