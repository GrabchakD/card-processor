package org.pb.service;

import org.pb.controller.dto.order.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderDto> getAll(Pageable pageable);

    OrderDto create(OrderDto orderDto, String operatorLogin);

    Long generateCardNumbersForNewOrders();

    Long countAllOrders();
}
