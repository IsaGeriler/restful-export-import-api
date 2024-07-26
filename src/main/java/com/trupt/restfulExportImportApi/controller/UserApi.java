package com.trupt.restfulExportImportApi.controller;

import com.trupt.restfulExportImportApi.dto.UserCreateDTO;
import com.trupt.restfulExportImportApi.dto.UserUpdateDTO;
import com.trupt.restfulExportImportApi.dto.UserViewDTO;
import com.trupt.restfulExportImportApi.exception.ReflectionUpdateException;
import com.trupt.restfulExportImportApi.exception.UserNotFound;
import com.trupt.restfulExportImportApi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Tag(name = "Users", description = "Endpoints for managing users, including CRUD operations, to create, retrieve, update, and delete user records.")
public class UserApi {
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users",
               description = "Retrieve a list of all users.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "List of users successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewDTO.class, type = "array"))),
                   @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
               })
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserViewDTO> users = userService.getAllUsers();
            users.forEach(user -> {
                user.add(linkTo(methodOn(UserApi.class).getUserById(user.getId())).withSelfRel());
                user.add(linkTo(methodOn(UserApi.class).updateUser(user.getId(), null)).withRel("update"));
                user.add(linkTo(methodOn(UserApi.class).patchUser(user.getId(), null)).withRel("patch"));
                user.add(linkTo(methodOn(UserApi.class).deleteUser(user.getId())).withRel("delete"));
            });
            Link allUsersLink = linkTo(methodOn(UserApi.class).getAllUsers()).withSelfRel();
            return ResponseEntity.ok(CollectionModel.of(users, allUsersLink));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by ID",
               description = "Retrieve a particular user by their ID.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "User successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewDTO.class, type = "array"))),
                   @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
                   @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
               })
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        try {
            UserViewDTO user = userService.getUserById(id);
            user.add(linkTo(methodOn(UserApi.class).getUserById(user.getId())).withSelfRel());
            user.add(linkTo(methodOn(UserApi.class).updateUser(user.getId(), null)).withRel("update"));
            user.add(linkTo(methodOn(UserApi.class).patchUser(user.getId(), null)).withRel("patch"));
            user.add(linkTo(methodOn(UserApi.class).deleteUser(user.getId())).withRel("delete"));
            return ResponseEntity.ok(user);
        } catch (UserNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Create a user",
               description = "Create a new user with the provided details.",
               responses = {
                   @ApiResponse(responseCode = "201", description = "User successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewDTO.class, type = "array"))),
                   @ApiResponse(responseCode = "400", description = "Bad request due to invalid input", content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
                   @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
               })
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        try {
            UserViewDTO createdUser = userService.createUser(userCreateDTO);
            createdUser.add(linkTo(methodOn(UserApi.class).getUserById(createdUser.getId())).withSelfRel());
            createdUser.add(linkTo(methodOn(UserApi.class).updateUser(createdUser.getId(), null)).withRel("update"));
            createdUser.add(linkTo(methodOn(UserApi.class).patchUser(createdUser.getId(), null)).withRel("patch"));
            createdUser.add(linkTo(methodOn(UserApi.class).deleteUser(createdUser.getId())).withRel("delete"));
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation errors: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/list")
    @Operation(summary = "Create multiple users",
               description = "Create multiple users from a given list of user details.",
               responses = {
                   @ApiResponse(responseCode = "201", description = "Users successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewDTO.class, type = "array"))),
                   @ApiResponse(responseCode = "400", description = "Bad request due to invalid input", content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
                   @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
               })
    public ResponseEntity<?> createUserList(@Valid @RequestBody List<UserCreateDTO> userCreateDTOList) {
        try {
            List<UserViewDTO> createdUsers = userService.createUserList(userCreateDTOList);
            createdUsers.forEach(createdUser -> {
                createdUser.add(linkTo(methodOn(UserApi.class).getUserById(createdUser.getId())).withSelfRel());
                createdUser.add(linkTo(methodOn(UserApi.class).updateUser(createdUser.getId(), null)).withRel("update"));
                createdUser.add(linkTo(methodOn(UserApi.class).patchUser(createdUser.getId(), null)).withRel("patch"));
                createdUser.add(linkTo(methodOn(UserApi.class).deleteUser(createdUser.getId())).withRel("delete"));
            });
            Link allUsersLink = linkTo(methodOn(UserApi.class).getAllUsers()).withSelfRel();
            return ResponseEntity.ok(CollectionModel.of(createdUsers, allUsersLink));
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation errors: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user by ID",
               description = "Update details of a particular user by their ID.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "User successfully updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewDTO.class, type = "array"))),
                   @ApiResponse(responseCode = "400", description = "Bad request due to invalid input", content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
                   @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
               })
    public ResponseEntity<?> updateUser(@PathVariable int id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        try {
            UserViewDTO updatedUser = userService.updateUser(id, userUpdateDTO);
            updatedUser.add(linkTo(methodOn(UserApi.class).getUserById(updatedUser.getId())).withSelfRel());
            updatedUser.add(linkTo(methodOn(UserApi.class).patchUser(updatedUser.getId(), null)).withRel("patch"));
            updatedUser.add(linkTo(methodOn(UserApi.class).deleteUser(updatedUser.getId())).withRel("delete"));
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation errors: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot leave a field empty/null: " + e.getMessage());
        } catch (ReflectionUpdateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update a user by ID",
               description = "Update specific details of a user by their ID. Only the fields provided in the request will be updated, leaving other fields unchanged.",
               responses = {
                    @ApiResponse(responseCode = "200", description = "User successfully updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewDTO.class, type = "array"))),
                    @ApiResponse(responseCode = "400", description = "Bad request due to invalid input", content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
              })
    public ResponseEntity<?> patchUser(@PathVariable int id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        try {
            UserViewDTO patchedUser = userService.patchUser(id, userUpdateDTO);
            patchedUser.add(linkTo(methodOn(UserApi.class).getUserById(patchedUser.getId())).withSelfRel());
            patchedUser.add(linkTo(methodOn(UserApi.class).updateUser(patchedUser.getId(), null)).withRel("update"));
            patchedUser.add(linkTo(methodOn(UserApi.class).deleteUser(patchedUser.getId())).withRel("delete"));
            return ResponseEntity.ok(patchedUser);
        } catch (UserNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation errors: " + e.getMessage());
        } catch (ReflectionUpdateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user by ID",
               description = "Delete a particular user by their ID.",
               responses = {
                   @ApiResponse(responseCode = "202", description = "User successfully deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewDTO.class, type = "array"))),
                   @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
               })
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.deleteUser(id));
        } catch (UserNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/slice")
    @Operation(summary = "Get users with pagination",
               description = "Retrieve a paged list of users.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "List of users successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewDTO.class, type = "array"))),
                   @ApiResponse(responseCode = "400", description = "Bad request due to invalid parameters", content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
                   @ApiResponse(responseCode = "404", description = "Page not found or no content available", content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
               })
    public ResponseEntity<?> slice(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) String sort) {
        if (page < 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request due to invalid parameter: " + page);

        if (size <= 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request due to invalid parameter: " + size);
        List<String> validSortProperties = Arrays.stream(UserViewDTO.class.getDeclaredFields())
                                                 .map(Field::getName)
                                                 .toList();

        if (sort != null && !validSortProperties.contains(sort))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid sort property: " + sort);
        Pageable pageable = PageRequest.of(page, size, sort != null ? Sort.by(sort) : Sort.unsorted());
        List<UserViewDTO> pagedUserList = userService.slice(pageable);

        if (pagedUserList.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Page not found or no content available");

        pagedUserList.forEach(pagedUser -> {
            pagedUser.add(linkTo(methodOn(UserApi.class).getUserById(pagedUser.getId())).withSelfRel());
            pagedUser.add(linkTo(methodOn(UserApi.class).updateUser(pagedUser.getId(), null)).withRel("update"));
            pagedUser.add(linkTo(methodOn(UserApi.class).patchUser(pagedUser.getId(), null)).withRel("patch"));
            pagedUser.add(linkTo(methodOn(UserApi.class).deleteUser(pagedUser.getId())).withRel("delete"));
        });
        Link allUsersLink = linkTo(methodOn(UserApi.class).getAllUsers()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(pagedUserList, allUsersLink));
    }
}