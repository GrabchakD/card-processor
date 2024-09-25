package org.pb.controller.dto.operator;

import lombok.Data;

@Data
public class OperatorDto {
    private String login;
    private String fullName;
    private Integer ordersCount;
}
