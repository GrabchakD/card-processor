package org.pb.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.pb.controller.dto.operator.OperatorDto;
import org.pb.model.statistic.Statistic;
import org.pb.service.ClientService;
import org.pb.service.OrderService;
import org.pb.service.StatisticService;
import org.pb.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.pb.utils.BaseUtils.map;
import static org.pb.utils.Constant.CSV_HEADER;
import static org.pb.utils.DateUtils.getFirstDayOfMonthFromCurrentDate;
import static org.pb.utils.DateUtils.getLastDayOfMonthFromCurrentDate;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final ClientService clientService;
    private final OrderService orderService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(StatisticServiceImpl.class);

    @Override
    public Statistic getStatistic() {
        logger.info("Generating statistic process");
        Statistic statistic = new Statistic();
        LocalDate currentDate = LocalDate.now();
        statistic.setClientsCount(clientService.countAllClients());
        statistic.setOrdersCount(orderService.countAllOrders());
        statistic.setOperators(userService.getOperatorsWithOrdersInPeriod(
                        getFirstDayOfMonthFromCurrentDate(currentDate),
                        getLastDayOfMonthFromCurrentDate(currentDate)).stream()
                .map(user -> map(user, OperatorDto.class).apply(modelMapper))
                .collect(toList()));
        return statistic;
    }

    @Override
    public byte[] generateSCVWithOperators(List<OperatorDto> operators) {
        logger.info("Generating operators CSV process");
        StringBuilder csvContent = new StringBuilder();
        csvContent.append(CSV_HEADER);

        for (OperatorDto operator : operators) {
            csvContent.append(operator.getLogin()).append(", ")
                    .append(operator.getFullName()).append(", ")
                    .append(operator.getOrdersCount()).append("\n");
        }

        return csvContent.toString().getBytes(StandardCharsets.UTF_8);
    }
}
