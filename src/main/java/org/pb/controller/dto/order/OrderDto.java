package org.pb.controller.dto.order;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private Long id;
    private LocalDateTime created;
    private String firstNameUA;
    private String lastNameUA;
    private String firstNameEN;
    private String lastNameEN;
    private String operatorFullName;
    private String cardType;
    private String cardNumber;
    private Long clientId;
}
