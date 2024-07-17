package com.trupt.restfulExportImportApi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserUpdateDTO {
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
    @DecimalMin(value = "2.0", message = "{restfulExportImportApi.constraints.weight.DecimalMin.message}")
    @DecimalMax(value = "635.0", message = "{restfulExportImportApi.constraints.weight.DecimalMax.message}")
    private BigDecimal weight;
}