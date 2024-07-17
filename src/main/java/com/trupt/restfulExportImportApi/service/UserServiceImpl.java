package com.trupt.restfulExportImportApi.service;

import com.trupt.restfulExportImportApi.dto.UserCreateDTO;
import com.trupt.restfulExportImportApi.dto.UserUpdateDTO;
import com.trupt.restfulExportImportApi.dto.UserViewDTO;
import com.trupt.restfulExportImportApi.exception.UserNotFound;
import com.trupt.restfulExportImportApi.model.User;
import com.trupt.restfulExportImportApi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<UserViewDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserViewDTO::of).collect(Collectors.toList());
    }

    @Override
    public UserViewDTO getUserById(int id) {
        return UserViewDTO.of(userRepository.findById(id).orElseThrow(() -> new UserNotFound("User not found!")));
    }

    @Override
    public UserViewDTO createUser(UserCreateDTO userCreateDTO) {
        User user = userRepository.save(new User(userCreateDTO.getName(), userCreateDTO.getSurname(),
                userCreateDTO.getHeight(), userCreateDTO.getWeight(), userCreateDTO.getBirthdate()));
        return UserViewDTO.of(user);
    }

    @Override
    public List<UserViewDTO> createUserList(List<UserCreateDTO> userCreateDTOList) {
        List<User> userList = userCreateDTOList.stream().map(userCreateDTO -> new User(userCreateDTO.getName(),
                        userCreateDTO.getSurname(), userCreateDTO.getHeight(),
                        userCreateDTO.getWeight(), userCreateDTO.getBirthdate())).collect(Collectors.toList());
        List<User> savedUsers = userRepository.saveAll(userList);
        return savedUsers.stream().map(UserViewDTO::of).collect(Collectors.toList());
    }

    @Override
    public UserViewDTO updateUser(int id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound("User not found"));
        user.setName(userUpdateDTO.getName());
        user.setSurname(userUpdateDTO.getSurname());
        user.setWeight(userUpdateDTO.getWeight());
        User updatedUser = userRepository.save(user);
        return UserViewDTO.of(updatedUser);
    }

    @Override
    public UserViewDTO deleteUser(int id) {
        User toBeDeleted = userRepository.findById(id).orElseThrow(() -> new UserNotFound("User not found"));
        userRepository.deleteById(id);
        return UserViewDTO.of(toBeDeleted);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<UserViewDTO> slice(Pageable pageable) {
        return userRepository.findAll(pageable).stream().map(UserViewDTO::of).collect(Collectors.toList());
    }
}