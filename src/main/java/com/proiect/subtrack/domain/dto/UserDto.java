package com.proiect.subtrack.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {


    private Long userId;

    private String name;

    private String email;

    private String passwordHash;

    private LocalDate dateOfBirth;

    private String address;

    private String password;
}
