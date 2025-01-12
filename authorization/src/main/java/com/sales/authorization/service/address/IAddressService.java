package com.sales.authorization.service.address;

import com.sales.authorization.entity.User;
import com.sales.authorization.pojo.request.UserRequest;
import com.sales.authorization.pojo.response.AddressResponse;

import java.util.List;

public interface IAddressService {
    void saveAddress(User user, String address);

    List<AddressResponse> getAddress(Long userId);

    void updateDefautAddress(UserRequest request, Long userId);
}
