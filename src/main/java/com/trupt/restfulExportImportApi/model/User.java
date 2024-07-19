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
    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @Column(name="`surname`")
    @NotNull
    @Size(min = 2, max = 50)
    private String surname;

    @Column(name="`age`")
    private int age;

    @Column(name="`height`")
    @NotNull
    @Min(value = 40)
    @Max(value = 273)
    private int height;

    @Column(name="`weight`")
    @NotNull
    @DecimalMin(value = "2.00")
    @DecimalMax(value = "635.00")
    private BigDecimal weight;

    @Column(name="`birthdate`")
    @NotNull
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