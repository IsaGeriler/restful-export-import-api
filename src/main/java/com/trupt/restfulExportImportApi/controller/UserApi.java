package com.trupt.restfulExportImportApi.controller;

import com.trupt.restfulExportImportApi.dto.UserCreateDTO;
import com.trupt.restfulExportImportApi.dto.UserUpdateDTO;
import com.trupt.restfulExportImportApi.dto.UserViewDTO;
import com.trupt.restfulExportImportApi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints for managing users, including CRUD operations, to create, retrieve, update, and delete user records.")
public class UserApi {
    private final UserService userService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Get all users",
               description = "Retrieve a list of all users.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "List of users successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewDTO.class, type = "array"))),
                   @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
               })
    public List<UserViewDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Get a user by ID",
               description = "Retrieve a particular user by their ID.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "User successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewDTO.class, type = "array"))),
                   @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
                   @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
               })
    public UserViewDTO getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create a user",
               description = "Create a new user with the provided details.",
               responses = {
                   @ApiResponse(responseCode = "201", description = "User successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewDTO.class, type = "array"))),
                   @ApiResponse(responseCode = "400", description = "Bad request due to invalid input", content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
                   @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
               })
    public UserViewDTO createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        return userService.createUser(userCreateDTO);
    }

    @PostMapping("/list")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Create multiple users",
               description = "Create multiple users from a given list of user details.",
               responses = {
                   @ApiResponse(responseCode = "201", description = "Users successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewDTO.class, type = "array"))),
                   @ApiResponse(responseCode = "400", description = "Bad request due to invalid input", content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
                   @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
               })
    public List<UserViewDTO> createUserList(@Valid @RequestBody List<UserCreateDTO> userCreateDTOList) {
        return userService.createUserList(userCreateDTOList);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Update a user by ID",
               description = "Update details of a particular user by their ID.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "User successfully updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewDTO.class, type = "array"))),
                   @ApiResponse(responseCode = "400", description = "Bad request due to invalid input", content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
                   @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
                   @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
              })
    public UserViewDTO updateUser(@PathVariable int id, @RequestBody UserUpdateDTO userUpdateDTO) {
        return userService.updateUser(id, userUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @Operation(summary = "Delete a user by ID",
               description = "Delete a particular user by their ID.",
               responses = {
                   @ApiResponse(responseCode = "202", description = "User successfully deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewDTO.class, type = "array"))),
                   @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
                   @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
               })
    public UserViewDTO deleteUser(@PathVariable int id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/slice")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Get users with pagination",
               description = "Retrieve a paged list of users.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "List of users successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewDTO.class, type = "array"))),
                   @ApiResponse(responseCode = "400", description = "Bad request due to invalid parameters", content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
                   @ApiResponse(responseCode = "404", description = "Page not found or no content available", content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
                   @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
               })
    public List<UserViewDTO> slice(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) String sort) {
        if (page < 0 || size <= 0) throw new IllegalArgumentException("Page number must be >= 0 and size must be > 0.");

        Pageable pageable = PageRequest.of(page, size, sort != null ? Sort.by(sort) : Sort.unsorted());
        List<UserViewDTO> pagedUserList = userService.slice(pageable);

        if (pagedUserList.isEmpty()) {
            throw new NoSuchElementException("No users found for the specified page and size.");
        }

        return pagedUserList;
    }
}