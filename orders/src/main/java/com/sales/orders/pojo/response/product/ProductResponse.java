package com.sales.orders.pojo.response.product;

import com.sales.orders.pojo.data.ProductEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Long id;
    String name;
    String description;
    Float price;
    Integer quantity;
    String imageUrl;
    Float sale;
    ProductEnum status;
    Boolean isActive;
}
