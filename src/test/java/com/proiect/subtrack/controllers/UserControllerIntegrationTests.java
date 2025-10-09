package com.proiect.subtrack.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.proiect.subtrack.TestDataUtil;
import com.proiect.subtrack.domain.dto.UserDto;
import com.proiect.subtrack.domain.entities.UserEntity;
import com.proiect.subtrack.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {


    @Autowired
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final UserService userService;

    @Autowired
    public UserControllerIntegrationTests(MockMvc mockMvc, UserService userService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.userService = userService;
    }


    @Test
    public void testThatCreateUserSuccesfullyReturnsHttp201Created() throws Exception {
        UserDto userDto = TestDataUtil.createUserDto();
        String json = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatCreateUserSuccesfullyReturnsUserDto() throws Exception {
        UserDto userDto = TestDataUtil.createUserDto();
        String json = objectMapper.writeValueAsString(userDto);


       MvcResult mvcResult= mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                ).andExpect(MockMvcResultMatchers.jsonPath("$.user_id").isNumber()
                ).andExpect(MockMvcResultMatchers.jsonPath("$.name").value(userDto.getName())
                ).andExpect(MockMvcResultMatchers.jsonPath("$.address").value(userDto.getAddress())
                ).andReturn();

       String response = mvcResult.getResponse().getContentAsString();

       UserDto responseDto = objectMapper.readValue(response , UserDto.class );

       assertThat(responseDto.getDate_of_birth()).isEqualTo(userDto.getDate_of_birth());

    }

    @Test
    public void testThatDeleteUserSuccesfullyReturnsHttp204NoContent() throws Exception {
        UserEntity userEntity = TestDataUtil.createUserEnitity();

        UserEntity savedEntity = userService.save(userEntity);

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + savedEntity.getUser_id())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatGetUserByIdSuccesfullyReturnsHttp200() throws Exception {
        UserEntity userEntity = TestDataUtil.createUserEnitity();

        UserEntity savedEntity = userService.save(userEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + savedEntity.getUser_id())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetUserByIdSuccesfullyReturnsUserDto() throws Exception {

        UserEntity userEntity = TestDataUtil.createUserEnitity();

        UserEntity savedEntity = userService.save(userEntity);

        MvcResult mvcResult= mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + savedEntity.getUser_id())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.jsonPath("$.user_id").isNumber()
        ).andExpect(MockMvcResultMatchers.jsonPath("$.name").value(savedEntity.getName())
        ).andExpect(MockMvcResultMatchers.jsonPath("$.address").value(savedEntity.getAddress())
        ).andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        UserDto responseDto = objectMapper.readValue(response , UserDto.class );

        assertThat(responseDto.getDate_of_birth()).isEqualTo(savedEntity.getDate_of_birth());

    }

    @Test
    public void testThatGetUsersSuccesfullyReturnsHttp200() throws Exception {
        UserEntity userEntity = TestDataUtil.createUserEnitity();
        UserEntity savedEntity = userService.save(userEntity);

        UserEntity userEntity1 = TestDataUtil.createUserEnitity();
        userEntity1.setName("asdasd");
        userService.save(userEntity1);

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetUsersSuccesfullyReturnsUserDtoPage() throws Exception {

        UserEntity userEntity = TestDataUtil.createUserEnitity();
        UserEntity savedEntity = userService.save(userEntity);

        UserEntity userEntity1 = TestDataUtil.createUserEnitity();
        userEntity1.setName("asdasd");
        userService.save(userEntity1);

        MvcResult mvcResult= mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[0].user_id").isNumber()
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value(savedEntity.getName())
        ).andExpect(MockMvcResultMatchers.jsonPath("$.content[0].address").value(savedEntity.getAddress())
        ).andReturn();


        String response = mvcResult.getResponse().getContentAsString();

        String jsonString = objectMapper.writeValueAsString(JsonPath.read(response, "$.content[0]")) ;
        UserDto responseDto = objectMapper.readValue(jsonString, UserDto.class);

        assertThat(responseDto.getDate_of_birth()).isEqualTo(savedEntity.getDate_of_birth());

    }

    @Test
    public void testThatUpdateUserSuccesfullyReturnsHttp200() throws Exception {
        UserEntity userEntity = TestDataUtil.createUserEnitity();
        UserEntity savedEntity =  userService.save(userEntity);


        mockMvc.perform(MockMvcRequestBuilders.patch("/users/" + savedEntity.getUser_id())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"asdasd\"}")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatUpdateUserSuccesfullyReturnsUserDto() throws Exception {

        UserEntity userEntity = TestDataUtil.createUserEnitity();
        UserEntity savedEntity = userService.save(userEntity);


        MvcResult mvcResult= mockMvc.perform(
                MockMvcRequestBuilders.patch("/users/" + savedEntity.getUser_id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"asdasd\"}")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.user_id").isNumber()
        ).andExpect(MockMvcResultMatchers.jsonPath("$.name").value("asdasd")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.address")
                .value(savedEntity.getAddress())
        ).andReturn();


        String response = mvcResult.getResponse().getContentAsString();

        UserDto responseDto = objectMapper.readValue(response, UserDto.class);

        assertThat(responseDto.getDate_of_birth()).isEqualTo(savedEntity.getDate_of_birth());

    }

}
