package com.trupt.restfulExportImportApi.controller;

import com.trupt.restfulExportImportApi.dto.UserCreateDTO;
import com.trupt.restfulExportImportApi.dto.UserUpdateDTO;
import com.trupt.restfulExportImportApi.dto.UserViewDTO;
import com.trupt.restfulExportImportApi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<UserViewDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public UserViewDTO getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserViewDTO createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        return userService.createUser(userCreateDTO);
    }

    @PostMapping("/list")
    @ResponseStatus(code = HttpStatus.CREATED)
    public List<UserViewDTO> createUserList(@Valid @RequestBody List<UserCreateDTO> userCreateDTOList) {
        return userService.createUserList(userCreateDTOList);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public UserViewDTO updateUser(@PathVariable int id, @RequestBody UserUpdateDTO userUpdateDTO) {
        return userService.updateUser(id, userUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public UserViewDTO deleteUser(@PathVariable int id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/slice")
    @ResponseStatus(code = HttpStatus.OK)
    public List<UserViewDTO> slice(Pageable pageable) {
        return userService.slice(pageable);
    }
}