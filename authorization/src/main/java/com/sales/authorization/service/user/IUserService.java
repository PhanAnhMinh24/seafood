package com.sales.authorization.service.user;

import com.sales.authorization.pojo.request.UserRequest;
import com.sales.authorization.pojo.response.UserResponse;

import java.util.List;

public interface IUserService {
    UserResponse getProfile();

    void updateProfile(UserRequest userRequest);

    List<UserResponse> findAll(List<Long> userIds);
}
