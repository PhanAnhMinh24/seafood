package com.sales.authorization.service.address;

import com.sales.authorization.entity.Address;
import com.sales.authorization.entity.User;
import com.sales.authorization.pojo.request.UserRequest;
import com.sales.authorization.pojo.response.AddressResponse;
import com.sales.authorization.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<AddressResponse> getAddress(Long userId) {
        List<Address> addresses = addressRepository.findByUserId(userId);
        return addresses.stream()
                .map(item -> AddressResponse.builder()
                        .id(item.getId())
                        .address(item.getAddressItem())
                        .isDefault(item.getIsDefault())
                        .build())
                .toList();
    }

    @Override
    public void updateDefautAddress(UserRequest request, Long userId) {
        List<Address> addresses = addressRepository.findByUserId(userId);
        for (Address address : addresses) {
            address.setIsDefault(address.getId().equals(request.getAddressId()));
            addressRepository.save(address);
        }
    }
}
