package com.trupt.restfulExportImportApi.dto;

import org.trupt.annotation.ExcelCellHeader;
import com.trupt.restfulExportImportApi.model.User;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserViewDTO {
    @ExcelCellHeader(headerName="AD", isRequired=true)
    private String name;

    @ExcelCellHeader(headerName="SOYAD", isRequired=true)
    private String surname;

    @ExcelCellHeader(headerName="YAS")
    private int age;

    @ExcelCellHeader(headerName="BOY", isRequired=true)
    private int height;

    @ExcelCellHeader(headerName="AGIRLIK", isRequired=true)
    private BigDecimal weight;

    @ExcelCellHeader(headerName="DOGUM TARIHI", isRequired=true)
    private LocalDate birthdate;

    public static UserViewDTO of(User user) {
        return new UserViewDTO(user.getName(), user.getSurname(), user.getAge(),
                user.getHeight(), user.getWeight(), user.getBirthdate());
    }
}