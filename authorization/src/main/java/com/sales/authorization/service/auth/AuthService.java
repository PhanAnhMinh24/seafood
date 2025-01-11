package com.sales.authorization.service.auth;

import com.sales.authorization.entity.User;
import com.sales.authorization.exception.AppException;
import com.sales.authorization.exception.ErrorCode;
import com.sales.authorization.pojo.request.LoginRequest;
import com.sales.authorization.pojo.request.SignupRequest;
import com.sales.authorization.pojo.response.JwtResponse;
import com.sales.authorization.repository.UserRepository;
import com.sales.authorization.service.address.IAddressService;
import com.sales.authorization.service.role.IRoleService;
import com.sales.authorization.service.user.UserDetailsImpl;
import com.sales.authorization.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final IRoleService roleService;
    private final IAddressService addressService;

    // Phương thức đăng ký
    @Override
    public User registerUser(SignupRequest signupRequest) {
        if (existEmail(signupRequest.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXIST);
        }
        User user = User.builder()
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword())) // Mã hóa mật khẩu
                .fullName(signupRequest.getFullName())
                .role(roleService.getRoleDefault())
                .build();
        User userSave = userRepository.save(user); // Lưu người dùng vào cơ sở dữ liệu
        addressService.saveAddress(userSave, signupRequest.getAddress()); // Lưu địa chỉ khi đã lưu người dùng
        return userSave;
    }

    private boolean existEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Phương thức đăng nhập
    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        try {
            // Thực hiện xác thực người dùng
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            String jwt = jwtUtils.generateToken(userDetails.getId(), userDetails.getUsername(), roles);

            return new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    roles);
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_USERNAME_OR_PASSWORD);
        }
    }
}
