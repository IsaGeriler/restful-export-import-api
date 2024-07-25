package com.trupt.restfulExportImportApi.util;

import com.trupt.restfulExportImportApi.dto.UserCreateDTO;
import com.trupt.restfulExportImportApi.dto.UserViewDTO;
import com.trupt.restfulExportImportApi.model.User;
import com.trupt.restfulExportImportApi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class UserProcessor {
    private final UserRepository userRepository;

    public List<User> convertToUserList(List<?> dtoList, Class<?> dtoClass) {
        if (UserCreateDTO.class.equals(dtoClass)) {
            return ((List<UserCreateDTO>) dtoList)
                    .stream()
                    .map(dto -> new User(dto.getName(), dto.getSurname(), dto.getHeight(), dto.getWeight(), dto.getBirthdate()))
                    .collect(Collectors.toList());
        } else if (UserViewDTO.class.equals(dtoClass)) {
            return ((List<UserViewDTO>) dtoList)
                    .stream()
                    .map(dto -> new User(dto.getName(), dto.getSurname(), dto.getHeight(), dto.getWeight(), dto.getBirthdate()))
                    .collect(Collectors.toList());
        }
        throw new IllegalArgumentException("Unsupported DTO class: " + dtoClass.getSimpleName());
    }

    public List<UserViewDTO> saveAndConvertToDTO(List<User> userList) {
        List<User> savedUsers = userRepository.saveAll(userList);
        return savedUsers
                .stream()
                .map(UserViewDTO::of)
                .collect(Collectors.toList());
    }
}