package org.pb.service;

import org.pb.controller.dto.operator.OperatorDto;
import org.pb.model.statistic.Statistic;

import java.util.List;

public interface StatisticService {
    Statistic getStatistic();

    byte[] generateSCVWithOperators(List<OperatorDto> operators);
}
