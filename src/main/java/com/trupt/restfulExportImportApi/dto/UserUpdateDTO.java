package com.trupt.restfulExportImportApi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserUpdateDTO {
    @Size(min = 2, max = 50)
    private String name;

    @Size(min = 2, max = 50)
    private String surname;

    @Min(value = 40)
    @Max(value = 273)
    private Integer height;

    @DecimalMin(value = "2.0")
    @DecimalMax(value = "635.0")
    private BigDecimal weight;
}