package com.sales.authorization.service.user;

import com.sales.authorization.pojo.request.UserRequest;
import com.sales.authorization.pojo.response.UserResponse;

public interface IUserService {
    UserResponse getProfile();

    void updateProfile(UserRequest userRequest);
}
