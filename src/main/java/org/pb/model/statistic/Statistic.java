package org.pb.model.statistic;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.pb.controller.dto.operator.OperatorDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class Statistic {
    private Long clientsCount;
    private Long ordersCount;
    private List<OperatorDto> operators = new ArrayList<>();
}
