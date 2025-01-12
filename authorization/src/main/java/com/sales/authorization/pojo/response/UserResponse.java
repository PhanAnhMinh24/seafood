package com.sales.authorization.pojo.response;

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
    List<AddressResponse> addressResponses;
    Boolean isSeller;
}
