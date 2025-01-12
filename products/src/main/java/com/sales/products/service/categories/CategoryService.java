package com.sales.products.service.categories;

import com.sales.products.entity.Categories;
import com.sales.products.exception.AppException;
import com.sales.products.exception.ErrorCode;
import com.sales.products.pojo.response.categories.CatagoryResponse;
import com.sales.products.repository.CatagoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Categories findByCategory(Long categoryId) {
        Optional<Categories> categories = catagoryRepository.findById(categoryId);
        if (categories.isEmpty()) {
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        return categories.get();
    }
}
