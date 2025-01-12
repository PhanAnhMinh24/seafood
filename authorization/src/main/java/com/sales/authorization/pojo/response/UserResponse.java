package com.sales.authorization.pojo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
