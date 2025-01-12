package com.sales.authorization.controller;

import com.sales.authorization.pojo.ApiResult;
import com.sales.authorization.pojo.request.UserRequest;
import com.sales.authorization.pojo.response.UserResponse;
import com.sales.authorization.service.user.IUserService;
import com.sales.authorization.utils.constants.EndpointConstants;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointConstants.USERS)
@AllArgsConstructor
@Log4j2
public class UserController {
    private final IUserService userService;

    @GetMapping(EndpointConstants.PROFILE)
    public ResponseEntity<ApiResult<UserResponse>> getProfile() {
        UserResponse userResponse = userService.getProfile();
        return ResponseEntity.ok().body(ApiResult.success(userResponse));
    }

    @PutMapping(EndpointConstants.PROFILE)
    public ResponseEntity<ApiResult<Boolean>> updateProfile(@RequestBody UserRequest request) {
        userService.updateProfile(request);
        return ResponseEntity.ok().body(ApiResult.success(Boolean.TRUE));
    }
}
