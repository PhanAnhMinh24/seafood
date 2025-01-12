package com.sales.authorization.pojo.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    String fullName;
    String phoneNumber;
    String profileImage;
    Long addressId;
    Boolean isSeller;
}
