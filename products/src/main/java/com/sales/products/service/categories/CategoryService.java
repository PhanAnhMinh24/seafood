package com.sales.products.service.categories;

import com.sales.products.pojo.response.categories.CatagoryResponse;
import com.sales.products.repository.CatagoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CatagoryRepository catagoryRepository;

    @Override
    public List<CatagoryResponse> getAll() {
        return catagoryRepository.findAll().stream()
                .map(item -> {
                    return CatagoryResponse.builder()
                            .id(item.getCategoryId())
                            .name(item.getName())
                            .build();
                }).toList();
    }
}
