package com.trupt.restfulExportImportApi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UserCreateDTO {
    @NotNull(message = "{restfulExportImportApi.constraints.name.NotNull.message}")
    @Size(min = 2, max = 50, message = "{restfulExportImportApi.constraints.name.Size.message}")
    private String name;

    @NotNull(message = "{restfulExportImportApi.constraints.surname.NotNull.message}")
    @Size(min = 2, max = 50, message = "{restfulExportImportApi.constraints.surname.Size.message}")
    private String surname;

    @NotNull(message = "{restfulExportImportApi.constraints.height.NotNull.message}")
    @Min(value = 40, message = "{restfulExportImportApi.constraints.height.Min.message}")
    @Max(value = 273, message = "{restfulExportImportApi.constraints.height.Max.message}")
    private int height;

    @NotNull(message = "{restfulExportImportApi.constraints.weight.NotNull.message}")
    @DecimalMin(value = "2.00", message = "{restfulExportImportApi.constraints.weight.DecimalMin.message}")
    @DecimalMax(value = "635.00", message = "{restfulExportImportApi.constraints.weight.DecimalMax.message}")
    private BigDecimal weight;

    @NotNull(message = "{restfulExportImportApi.constraints.birthdate.NotNull.message}")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
}