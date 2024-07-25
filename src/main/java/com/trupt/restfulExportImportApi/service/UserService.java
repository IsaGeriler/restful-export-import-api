package com.trupt.restfulExportImportApi.service;

import com.trupt.restfulExportImportApi.dto.UserCreateDTO;
import com.trupt.restfulExportImportApi.dto.UserUpdateDTO;
import com.trupt.restfulExportImportApi.dto.UserViewDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<UserViewDTO> getAllUsers();
    UserViewDTO getUserById(int id);
    UserViewDTO createUser(UserCreateDTO userCreateDTO);
    List<UserViewDTO> createUserList(List<UserCreateDTO> userCreateDTOList);
    UserViewDTO updateUser(int id, UserUpdateDTO userUpdateDTO);
    UserViewDTO patchUser(int id, UserUpdateDTO userUpdateDTO);
    UserViewDTO deleteUser(int id);
    List<UserViewDTO> slice(Pageable pageable);
}