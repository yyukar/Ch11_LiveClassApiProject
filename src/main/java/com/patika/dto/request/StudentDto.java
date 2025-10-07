package com.patika.dto.request;

import com.patika.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    @Column(length = 30)
    private String firstName;

    @Column(length = 30)
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Long connectionId;

    private Long departmentId;

}
