package org.pb.controller.dto.client;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientDto {
    private Long id;
    private String firstNameUa;
    private String lastNameUa;
    private String firstNameEn;
    private String lastNameEn;
    private LocalDate birth;
    private String phone;
    private String email;
    private String operatorFullName;
}
