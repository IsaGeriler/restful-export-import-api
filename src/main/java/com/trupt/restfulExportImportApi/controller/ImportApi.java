package com.trupt.restfulExportImportApi.controller;

import com.trupt.restfulExportImportApi.dto.UserViewDTO;
import com.trupt.restfulExportImportApi.service.ImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/import")
@Tag(name = "Import", description = "Operations related for importing data from an Excel file to a database.")
public class ImportApi {
    private final ImportService importService;

    @PostMapping(path = "/users", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Import user data from an Excel file to a database.",
               description = "Imports user data from an Excel file into a database, " +
                             "validating and mapping the data according to the defined schema. " +
                             "Ensure that the Excel file adheres to the required format for a successful import.",
               requestBody = @RequestBody(content = @Content(mediaType = "multipart/form-data")),
               responses = {
                   @ApiResponse(responseCode = "200", description = "Successful import of user data.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewDTO.class, type = "array"))),
                   @ApiResponse(responseCode = "400", description = "Invalid file format or content. The uploaded file may be missing required data or in an incorrect format.", content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
                   @ApiResponse(responseCode = "415", description = "Unsupported media type. The uploaded file is not in a supported format.", content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
                   @ApiResponse(responseCode = "500", description = "Internal server error. An error occurred while processing the file or saving data to the database.", content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
               }
    )
    public ResponseEntity<?> importUserFile(@RequestPart MultipartFile file) {
        // Check file MIME type
        if (!"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(file.getContentType())) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Only .xlsx files are supported.");
        }

        try {
            List<UserViewDTO> importedUsers = importService.importUserFile(file, UserViewDTO.class);
            return ResponseEntity.ok(importedUsers);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The uploaded file may be missing required data or in an incorrect format.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The uploaded file may be missing required data.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the file or saving data to the database.");
        }
    }
}