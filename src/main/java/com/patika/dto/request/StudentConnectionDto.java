package com.patika.dto.request;

import com.patika.enums.Gender;
import jakarta.persistence.Column;
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
public class StudentConnectionDto {
    @Column(length = 30)
    private String firstName;

    @Column(length = 30)
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Long departmentId;

    private ConnectionStudent connectionStudent;


    @Getter
    @Setter
    public class ConnectionStudent{
        private String email;

        private String mobileNumber;

    }
}
