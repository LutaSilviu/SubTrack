package com.proiect.subtrack.controllers;

import com.proiect.subtrack.domain.dto.UserDto;
import com.proiect.subtrack.domain.entities.UserEntity;
import com.proiect.subtrack.mappers.impl.UserMapperImpl;
import com.proiect.subtrack.services.UserService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController()
public class UserControllers {


    private final UserService userService;
    private final UserMapperImpl userMapper;

    public UserControllers(UserService userService, UserMapperImpl userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity responseEntity = userService.save(userEntity);
        return new ResponseEntity<>(userMapper.mapTo(responseEntity), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public Page<UserDto> getAllUsers(Pageable pageable){
        Page<UserEntity> userEntityList = userService.getAll(pageable);
        return userEntityList.map(userMapper::mapTo);

    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUsersById(@PathVariable("id") Long id){
        Optional<UserEntity> userEntity = userService.getUserById(id);
        return userEntity.map(userEntity1 ->
                new ResponseEntity<>(userMapper.mapTo(userEntity1),HttpStatus.OK)
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id){
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<UserDto> partialUpdateUser(@PathVariable("id")Long id, @RequestBody UserDto userDto){
      if(!userService.exists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      UserEntity userEntity = userMapper.mapFrom(userDto);
      UserEntity updatedUserEntity = userService.partialUpdate(id, userEntity);
      return new ResponseEntity<>(userMapper.mapTo(updatedUserEntity), HttpStatus.OK);
    }
}
