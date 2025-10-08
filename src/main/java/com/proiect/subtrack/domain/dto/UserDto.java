package com.proiect.subtrack.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {


    private Long user_id;

    private String name;

    private Date date_of_birth;

    private String address;
}
