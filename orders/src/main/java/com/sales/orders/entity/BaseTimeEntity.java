package com.sales.orders.entity;

import com.sales.orders.utils.DateTimeUtils;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
public abstract class BaseTimeEntity {
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @PreUpdate
    public void updateInit() {
        this.updatedAt = DateTimeUtils.getDateTimeNow();
    }

    @PrePersist
    public void createInit() {
        this.createdAt = DateTimeUtils.getDateTimeNow();
        this.updatedAt = DateTimeUtils.getDateTimeNow();
    }
}
