package com.sales.products.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Categories extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    Long categoryId;

    @Column(unique = true, nullable = false)
    String name;

    String description;

    @Column(name = "parent_category_id")
    Long parentCategoryId;

    @OneToMany(mappedBy = "category")
    List<Products> products;
}
