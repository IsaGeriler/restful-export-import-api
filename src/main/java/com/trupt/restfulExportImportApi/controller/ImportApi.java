package com.trupt.restfulExportImportApi.controller;

import com.trupt.restfulExportImportApi.dto.UserViewDTO;
import com.trupt.restfulExportImportApi.service.ImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/import")
@Tag(name = "Importer")
public class ImportApi {
    private final ImportService importService;

    @PostMapping(path = "/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(description = "Import the data from a .xlsx file to the user's database")
    public List<UserViewDTO> importUserFile(@RequestPart MultipartFile file) throws Exception {
        return importService.importUserFile(file, UserViewDTO.class);
    }
}