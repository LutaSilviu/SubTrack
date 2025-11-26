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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController()
@RequestMapping("/users")
@Tag(name = "Users", description = "User management endpoints")
public class UserControllers {


    private final UserService userService;
    private final UserMapperImpl userMapper;

    public UserControllers(UserService userService, UserMapperImpl userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    @Operation(summary = "Create user", description = "Create a new user from DTO")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity responseEntity = userService.save(userEntity);
        return new ResponseEntity<>(userMapper.mapTo(responseEntity), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get users (paged)", description = "Get all users with pagination")
    public Page<UserDto> getAllUsers(Pageable pageable){
        Page<UserEntity> userEntityList = userService.getAll(pageable);
        return userEntityList.map(userMapper::mapTo);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id", description = "Returns a single user by id")
    public ResponseEntity<UserDto> getUsersById(@PathVariable("id") Long id){
        Optional<UserEntity> userEntity = userService.getUserById(id);
        return userEntity.map(userEntity1 ->
                new ResponseEntity<>(userMapper.mapTo(userEntity1),HttpStatus.OK)
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Deletes a user by id")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id){
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partial update user", description = "Partially updates a user by id")
    public ResponseEntity<UserDto> partialUpdateUser(@PathVariable("id")Long id, @RequestBody UserDto userDto){
      if(!userService.exists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      UserEntity userEntity = userMapper.mapFrom(userDto);
      UserEntity updatedUserEntity = userService.partialUpdate(id, userEntity);
      return new ResponseEntity<>(userMapper.mapTo(updatedUserEntity), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
        Optional<UserEntity> userEntity = userService.getUserByEmail(email);

        return userEntity.map(userEntity1 -> new ResponseEntity<>(userMapper.mapTo(userEntity1),HttpStatus.OK)).orElse(
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }
}
