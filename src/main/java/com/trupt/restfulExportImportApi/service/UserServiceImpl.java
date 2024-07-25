package com.trupt.restfulExportImportApi.service;

import com.trupt.restfulExportImportApi.dto.UserCreateDTO;
import com.trupt.restfulExportImportApi.dto.UserUpdateDTO;
import com.trupt.restfulExportImportApi.dto.UserViewDTO;
import com.trupt.restfulExportImportApi.exception.ReflectionUpdateException;
import com.trupt.restfulExportImportApi.exception.UserNotFound;
import com.trupt.restfulExportImportApi.model.User;
import com.trupt.restfulExportImportApi.repository.UserRepository;
import com.trupt.restfulExportImportApi.util.UserProcessor;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserProcessor userProcessor;

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
        List<User> userList = userProcessor.convertToUserList(userCreateDTOList, UserCreateDTO.class);
        return userProcessor.saveAndConvertToDTO(userList);
    }

    @Override
    public UserViewDTO updateUser(int id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound("User not found"));

        Field[] fields = UserUpdateDTO.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(userUpdateDTO);
                if (value == null){
                    throw new IllegalArgumentException(field.getName() + " cannot be null");
                }
                System.out.println("Updating field: " + field.getName() + " with value: " + value);
                Field userField = User.class.getDeclaredField(field.getName());
                userField.setAccessible(true);
                userField.set(user, value);
            } catch (NoSuchFieldException e) {
                throw new ReflectionUpdateException("Field not found in User entity: " + field.getName(), e);
            } catch (IllegalAccessException e) {
                throw new ReflectionUpdateException("Unable to update field: " + field.getName(), e);
            } finally {
                field.setAccessible(false);
            }
        }
        User updatedUser = userRepository.save(user);
        return UserViewDTO.of(updatedUser);
    }

    @Override
    public UserViewDTO patchUser(int id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound("User not found"));

        Field[] fields = UserUpdateDTO.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(userUpdateDTO);
                if (value != null) {
                    System.out.println("Updating field: " + field.getName() + " with value: " + value);
                    Field userField = User.class.getDeclaredField(field.getName());
                    userField.setAccessible(true);
                    userField.set(user, value);
                }
            } catch (NoSuchFieldException e) {
                throw new ReflectionUpdateException("Field not found in User entity: " + field.getName(), e);
            } catch (IllegalAccessException e) {
                throw new ReflectionUpdateException("Unable to update field: " + field.getName(), e);
            } finally {
                field.setAccessible(false);
            }
        }
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