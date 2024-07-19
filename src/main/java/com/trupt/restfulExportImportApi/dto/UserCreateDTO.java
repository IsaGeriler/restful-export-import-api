package com.trupt.restfulExportImportApi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UserCreateDTO {
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
    @DecimalMin(value = "2.00")
    @DecimalMax(value = "635.00")
    private BigDecimal weight;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
}