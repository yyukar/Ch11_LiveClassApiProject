package com.patika.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Department extends BaseEntity{


    @Column(name = "department_Name", nullable = false)
    private String departmentName;

    @NotNull
    @Column(unique = true)
    private String departmentCode;


}
