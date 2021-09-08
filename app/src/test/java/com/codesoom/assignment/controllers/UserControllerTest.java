package com.codesoom.assignment.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.CreateUserDto;
import com.codesoom.assignment.dto.UpdateUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Nested
    @DisplayName("POST /users 요청은")
    class Describe_postUsers {

        @Nested
        @DisplayName("유효한 유저 생성 DTO가 주어질 때")
        class Context_validUser {

            private CreateUserDto validCreateUserDto;

            @BeforeEach
            void setUp() {
                validCreateUserDto = CreateUserDto.builder()
                    .name("name")
                    .email("email")
                    .password("password")
                    .build();
            }

            @Test
            @DisplayName("생성한 유저를 리턴하고 201을 응답한다")
            void it_returns_created_user_and_response_201() throws Exception {
                mockMvc.perform(
                    post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(validCreateUserDto))
                )
                    .andExpect(status().isCreated())
                    .andExpect(content().json(toJson(validCreateUserDto.toEntity())));
            }
        }

        @Nested
        @DisplayName("유효하지 않은 유저 생성 DTO가 주어질 때")
        class Context_invalidCreateUserDto {

            private CreateUserDto invalidCreateUserDto;

            @BeforeEach
            void setUp() {
                invalidCreateUserDto = CreateUserDto.builder()
                    .name("name")
                    .email("email")
                    .build();
            }

            @Test
            @DisplayName("에러를 던지고 400을 응답한다")
            void it_throws_and_response_400() throws Exception {
                mockMvc.perform(
                    post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonWithoutPassword(invalidCreateUserDto))
                )
                    .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("PATCH /users/{id} 요청은")
    class Describe_patchUsersWithId {

        @Nested
        @DisplayName("유효한 유저 업데이트 DTO가 주어진다면")
        class Context_validUpdateUserDto {

            private UpdateUserDto validUpdateUserDto;

            @BeforeEach
            void setUp() {
                validUpdateUserDto = UpdateUserDto.builder()
                    .name("name")
                    .email("email")
                    .password("password")
                    .build();
            }

            @Nested
            @DisplayName("id가 존재한다면")
            class Context_idExist {

                private Long existId;

                @BeforeEach
                void setUp() {
                    User createdUser = userRepository.save(
                        validUpdateUserDto.toEntity()
                    );

                    Long id = createdUser.getId();
                    assertThat(userRepository.existsById(id))
                        .isTrue();

                    existId = id;
                }

                @Test
                @DisplayName("수정된 유저를 리턴하고 200을 응답한다")
                void it_returns_updated_user_and_response_200() throws Exception {
                    mockMvc.perform(
                        patch("/users/" + existId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(validUpdateUserDto))
                    )
                        .andExpect(status().isOk())
                        .andExpect(content().json(toJson(validUpdateUserDto.toEntity())));
                }
            }

            @Nested
            @DisplayName("id가 존재하지 않는다면")
            class Context_idNotExist {

                private Long notExistId;

                @BeforeEach
                void setUp() {
                    User user = userRepository.save(
                        validUpdateUserDto.toEntity()
                    );

                    userRepository.deleteAll();

                    Long id = user.getId();
                    assertThat(userRepository.existsById(id))
                        .isFalse();

                    notExistId = id;
                }

                @Test
                @DisplayName("404를 응답한다")
                void it_response_404() throws Exception {
                    mockMvc.perform(
                        patch("/users/" + notExistId)

                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(validUpdateUserDto))
                    )
                        .andExpect(status().isNotFound());
                }
            }
        }
    }

    private String toJson(UpdateUserDto updateUserDto) {
        return String.format(
            "{\"name\": \"%s\","
                + " \"email\": \"%s\","
                + " \"password\": \"%s\"}",
            updateUserDto.getName(),
            updateUserDto.getEmail(),
            updateUserDto.getPassword());
    }

    private String toJson(User user) {
        return String.format(
            "{\"name\": \"%s\","
                + " \"email\": \"%s\","
                + " \"password\": \"%s\"}",
            user.getName(),
            user.getEmail(),
            user.getPassword());
    }

    private String toJson(CreateUserDto createUserDto) {
        return String.format(
            "{\"name\": \"%s\","
                + " \"email\": \"%s\","
                + " \"password\": \"%s\"}",
            createUserDto.getName(),
            createUserDto.getEmail(),
            createUserDto.getPassword());
    }

    private String toJsonWithoutPassword(CreateUserDto createUserDto) {
        return String.format(
            "{\"name\": \"%s\","
                + " \"email\": \"%s\"}",
            createUserDto.getName(),
            createUserDto.getEmail());
    }
}
