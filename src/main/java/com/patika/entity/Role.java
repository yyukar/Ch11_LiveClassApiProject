package com.patika.entity;

import com.patika.enums.RoleType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private RoleType type;
}
