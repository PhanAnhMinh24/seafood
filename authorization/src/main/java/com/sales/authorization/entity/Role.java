package com.sales.authorization.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private String description;

    @Column(name = "is_default")
    private Boolean isDefault;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<User> users;
}