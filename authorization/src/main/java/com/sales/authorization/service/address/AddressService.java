package com.sales.authorization.service.address;

import com.sales.authorization.entity.Address;
import com.sales.authorization.entity.User;
import com.sales.authorization.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService implements IAddressService {
    private final AddressRepository addressRepository;

    @Override
    public void saveAddress(User user, String address) {
        Address addressSave = Address.builder()
                .addressItem(address)
                .user(user)
                .isDefault(true)
                .build();
        addressRepository.save(addressSave);
    }
}
