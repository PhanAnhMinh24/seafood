package com.sales.products.repository;

import com.sales.products.entity.Products;
import com.sales.products.pojo.response.products.ProductResponse;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

//    List<Products> getLatestProducts(Pageable pageable);

}
