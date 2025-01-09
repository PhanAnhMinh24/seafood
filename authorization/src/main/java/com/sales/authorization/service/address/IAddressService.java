package com.sales.authorization.service.address;

import com.sales.generic.entity.User;

public interface IAddressService {
    void saveAddress(User user, String address);
}
