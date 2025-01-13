package com.sales.authorization.service.user;

import com.sales.authorization.entity.Role;
import com.sales.authorization.entity.User;
import com.sales.authorization.exception.AppException;
import com.sales.authorization.exception.ErrorCode;
import com.sales.authorization.pojo.request.UserRequest;
import com.sales.authorization.pojo.response.UserResponse;
import com.sales.authorization.repository.UserRepository;
import com.sales.authorization.service.BaseService;
import com.sales.authorization.service.address.IAddressService;
import com.sales.authorization.service.role.IRoleService;
import com.sales.authorization.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final IAddressService addressService;
    private final HttpServletRequest request;
    private final JwtUtils jwtUtils;
    private final IRoleService roleService;

    @Override
    public UserResponse getProfile() {
        Long userId = BaseService.getUserId(request, jwtUtils);
        User user = findById(userId);
        UserResponse userResponse = mapUserResponse(user);
        userResponse.setAddressResponses(addressService.getAddress(user.getId()));
        return userResponse;
    }

    /**
     * Kiểm tra người dùng có phải là người bán hay không
     */
    private boolean checkIfSeller(User user) {
        try {
            Role sellerRole = roleService.getRoleSeller();
            return sellerRole.getRoleId().equals(user.getRole().getRoleId());
        } catch (Exception e) {
            // Log lỗi nếu cần
            return false;
        }
    }


    @Override
    public void updateProfile(UserRequest userRequest) {
        Long userId = BaseService.getUserId(request, jwtUtils);
        User user = findById(userId);

        addressService.updateDefautAddress(userRequest, userId);

        user.setFullName(userRequest.getFullName());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setProfileImage(userRequest.getProfileImage());

        if (userRequest.getIsSeller()) {
            Role role = roleService.getRoleSeller();
            user.setRole(role);
        }

        userRepository.save(user);
    }

    @Override
    public List<UserResponse> findAll(List<Long> userIds) {
        return userRepository.findByIdIn(userIds).stream().map(this::mapUserResponse).toList();
    }

    private UserResponse mapUserResponse(User user) {
        boolean isSeller = checkIfSeller(user);
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .roleId(user.getRole().getRoleId())
                .roleName(user.getRole().getName())
                .isSeller(isSeller)
                .build();
    }

    private User findById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        return user.get();
    }
}
