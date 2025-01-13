package com.sales.orders.pojo.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PagingRequest<T> {
    String sortKey;
    T filters;
    Integer page;
    Integer size;
    Sort.Direction sortBy;
}
