package com.trupt.restfulExportImportApi.controller;

import com.trupt.restfulExportImportApi.dto.UserViewDTO;
import com.trupt.restfulExportImportApi.service.ImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/import")
public class ImportApi {
    private final ImportService importService;

    @PostMapping("/user")
    @ResponseStatus(code = HttpStatus.OK)
    public List<UserViewDTO> importUserFile(@RequestParam("file") MultipartFile file) throws Exception {
        return importService.importUserFile(file, UserViewDTO.class);
    }
}