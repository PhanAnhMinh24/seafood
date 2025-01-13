package com.sales.products.pojo.response.users;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String email;
    String fullName;
    String phoneNumber;
    String profileImage;
    Long roleId;
    String roleName;
    Boolean isSeller;
}
