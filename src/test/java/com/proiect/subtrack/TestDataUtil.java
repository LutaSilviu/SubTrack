package com.proiect.subtrack;

import com.proiect.subtrack.domain.dto.UserDto;
import com.proiect.subtrack.domain.entities.UserEntity;

import java.util.Date;

public class TestDataUtil {

    public static UserEntity createUserEnitity(){
       return UserEntity.builder().name("Nume").address("Adresa").date_of_birth(new Date()).build();
    }

    public static UserDto createUserDto(){
        return UserDto.builder().name("Nume").address("Adresa").date_of_birth(new Date()).build();
    }
}
