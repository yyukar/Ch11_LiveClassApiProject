package com.patika.entity;

import com.patika.enums.Gender;
import com.patika.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student extends BaseEntity{
    @Column(length = 30)
    private String firstName;

    @Column(length = 30)
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "connection_id")
    private Connection connection;


    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;



}
