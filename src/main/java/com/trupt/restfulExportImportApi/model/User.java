package com.trupt.restfulExportImportApi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Data
@Entity
@NoArgsConstructor
@Table(name="`users`")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="`id`")
    private int id;

    @Column(name="`name`")
    @NotNull(message = "{restfulExportImportApi.constraints.name.NotNull.message}")
    @Size(min = 2, max = 50, message = "{restfulExportImportApi.constraints.name.Size.message}")
    private String name;

    @Column(name="`surname`")
    @NotNull(message = "{restfulExportImportApi.constraints.surname.NotNull.message}")
    @Size(min = 2, max = 50, message = "{restfulExportImportApi.constraints.surname.Size.message}")
    private String surname;

    @Column(name="`age`")
    private int age;

    @Column(name="`height`")
    @NotNull(message = "{restfulExportImportApi.constraints.height.NotNull.message}")
    @Min(value = 40, message = "{restfulExportImportApi.constraints.height.Min.message}")
    @Max(value = 273, message = "{restfulExportImportApi.constraints.height.Max.message}")
    private int height;

    @Column(name="`weight`")
    @NotNull(message = "{restfulExportImportApi.constraints.weight.NotNull.message}")
    @DecimalMin(value = "2.00", message = "{restfulExportImportApi.constraints.weight.DecimalMin.message}")
    @DecimalMax(value = "635.00", message = "{restfulExportImportApi.constraints.weight.DecimalMax.message}")
    private BigDecimal weight;

    @Column(name="`birthdate`")
    @NotNull(message = "{restfulExportImportApi.constraints.birthdate.NotNull.message}")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    public User(String name, String surname, int height, BigDecimal weight, LocalDate birthdate) {
        this.name = name;
        this.surname = surname;
        this.height = height;
        this.weight = weight;
        this.birthdate = birthdate;
        age = (birthdate != null) ? Period.between(birthdate, LocalDate.now()).getYears() : 0;
    }
}