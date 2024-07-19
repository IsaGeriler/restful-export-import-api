package com.trupt.restfulExportImportApi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserUpdateDTO {
    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @NotNull
    @Size(min = 2, max = 50)
    private String surname;

    @NotNull
    @Min(value = 40)
    @Max(value = 273)
    private int height;

    @NotNull
    @DecimalMin(value = "2.0")
    @DecimalMax(value = "635.0")
    private BigDecimal weight;
}