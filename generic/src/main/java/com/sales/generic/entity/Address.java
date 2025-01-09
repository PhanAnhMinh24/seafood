package com.sales.generic.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    Long id;

    @Column(columnDefinition = "TEXT", name = "address_item")
    String addressItem;

    @Column(name = "is_default")
    Boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
