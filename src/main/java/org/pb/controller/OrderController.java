package org.pb.controller;

import lombok.RequiredArgsConstructor;
import org.pb.controller.dto.common.PageableExt;
import org.pb.controller.dto.order.OrderDto;
import org.pb.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping
    public ResponseEntity<PageableExt<OrderDto>> getAll(@PageableDefault Pageable pageable) {
        logger.info("Attempt to get all orders");
        return PageableExt.ofResponse(orderService.getAll(pageable));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping
    public ResponseEntity<OrderDto> create(@RequestBody OrderDto orderDto, Principal principal) {
        logger.info(String.format("Attempt to create order: %s", orderDto));
        return ResponseEntity.ok(orderService.create(orderDto, principal.getName()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/generate-card-numbers")
    public ResponseEntity<Long> generateCardNumbersForNewOrders() {
        logger.info("Attempt to generate card numbers for new orders");
        return ResponseEntity.ok(orderService.generateCardNumbersForNewOrders());
    }
}
