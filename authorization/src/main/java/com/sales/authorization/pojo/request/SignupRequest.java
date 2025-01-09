package com.sales.authorization.pojo.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignupRequest {
    String email;
    String password;
    String fullName;
    String phoneNumber;
    String address;
    String profileImage;
}
