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
    @ExcelCellHeader(headerName="userName", isRequired = true)
    private String name;

    @ExcelCellHeader(headerName="userSurname", isRequired = true)
    private String surname;

    @ExcelCellHeader(headerName="userAge", calculateAverage = true, calculateSum = true)
    private int age;

    @ExcelCellHeader(headerName="userHeight", isRequired = true, calculateAverage = true)
    private Integer height;

    @ExcelCellHeader(headerName="userWeight", isRequired = true, calculateAverage = true)
    private BigDecimal weight;

    @ExcelCellHeader(headerName="userBirthdate", isRequired = true)
    private LocalDate birthdate;

    public static UserViewDTO of(User user) {
        return new UserViewDTO(user.getName(),
                               user.getSurname(),
                               user.getAge(),
                               user.getHeight(),
                               user.getWeight(),
                               user.getBirthdate());
    }
}