package com.sales.generic.entity;

import com.sales.generic.utils.DateTimeUtils;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseTimeEntity {
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
