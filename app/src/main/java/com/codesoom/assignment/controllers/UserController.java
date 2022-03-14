// 1. POST /users -> 회원 생성
// 2. PATCH /users/{id} -> 회원 수정
// 3. DELETE /users/{id} -> 회원 삭제

package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserRegistrationData;
import com.codesoom.assignment.dto.UserModificationData;
import com.codesoom.assignment.dto.UserResultData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 회원의 Http 요청을 처리합니다.
 * */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private UserResultData getUserResultData(User user) {
        return UserResultData.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    /**
     * 회원 정보를 받아 새로운 회원을 생성하여 리턴합니다.
     *
     * @param  registrationData 생성하려는 회원의 정보 입니다
     * @return 새로 생성된 회원
     * */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResultData createUser(@RequestBody @Valid UserRegistrationData registrationData) {
        User user = userService.create(registrationData);
        return getUserResultData(user);
    }

    /**
     * 요청받은 id의 회원을 찾아 요청받은 회원 정보로 변경하고 변경된 회원을 리턴합니다.
     *
     * @param id 요청 id
     * @param modificationData 요청 회원 정보
     * @return 변경된 회원 정보
     * */
    @PatchMapping("{id}")
    public UserResultData updateUser(
            @PathVariable Long id,
            @RequestBody @Valid UserModificationData modificationData
    ) {
        User user = userService.update(id, modificationData);
        return getUserResultData(user);
    }

    /**
     * 요청받은 id의 유저를 제거합니다.
     *
     * @param id 요청 id
     * */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}