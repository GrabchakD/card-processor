package org.pb.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.pb.controller.dto.order.OrderDto;
import org.pb.controller.exception.EntityNotFoundException;
import org.pb.dao.ClientDao;
import org.pb.dao.OrderDao;
import org.pb.model.order.Order;
import org.pb.service.CardService;
import org.pb.service.OrderService;
import org.pb.service.UserService;
import org.pb.utils.PageCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;
import static org.pb.utils.BaseUtils.map;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final ModelMapper modelMapper;
    private final CardService cardService;
    private final ClientDao clientDao;
    private final UserService userService;
    private static Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public Page<OrderDto> getAll(Pageable pageable) {
        logger.info("Getting all orders process");
        List<OrderDto> orders = orderDao.findAllByOrderByIdDesc().stream()
                .map(order -> map(order, OrderDto.class).apply(modelMapper))
                .collect(toList());

        return new PageCreator<>(orders, pageable).pageFromList();
    }

    @Override
    public OrderDto create(OrderDto orderDto, String operatorLogin) {
        logger.info(String.format("Create order: %s process", orderDto));
        return clientDao.findById(orderDto.getClientId())
                .map(client -> {
                    Order payload = map(orderDto, Order.class).apply(modelMapper);
                    payload.setClient(client);
                    payload.setOperator(userService.findByLogin(operatorLogin));
                    payload.setCreated(LocalDateTime.now());
                    return payload;
                })
                .map(orderDao::save)
                .map(order -> map(order, OrderDto.class).apply(modelMapper))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Client with id: %s, not found", orderDto.getClientId())));
    }

    @Override
    public Long generateCardNumbersForNewOrders() {
        logger.info("Generating card numbers for new orders process");
        List<Order> orders = orderDao.findAllByCardNumberIsNull();

        orders.forEach(order -> {
                    String cardBin = cardService.findBinByCardType(order.getCardType()).getBin();
                    String cardNumber = generateUniqueCardNumber(cardBin);
                    order.setCardNumber(cardNumber);
                });

        orderDao.saveAll(orders);
        return (long) orders.size();
    }

    @Override
    public Long countAllOrders() {
        logger.info("Count all orders process");
        return orderDao.count();
    }

    private String generateUniqueCardNumber(String BIN) {
        String cardNumber;
        do {
            cardNumber = generateCardNumber(BIN);
        } while (isCardNumberInDatabase(cardNumber));

        return cardNumber;
    }

    private String generateCardNumber(String prefix) {
        if (prefix.length() != 6) {
            throw new IllegalArgumentException("BIN must be 6 digits long");
        }

        StringBuilder cardNumber = new StringBuilder(prefix);
        for (int i = 0; i < 9; i++) {
            cardNumber.append(random.nextInt(10));
        }

        int controlDigit = calculateLuhnCheckDigit(cardNumber.toString());
        cardNumber.append(controlDigit);

        return cardNumber.toString();
    }

    private int calculateLuhnCheckDigit(String cardNumberWithoutCheckDigit) {
        int sum = 0;
        boolean alternate = true;

        for (int i = cardNumberWithoutCheckDigit.length() - 1; i >= 0; i--) {
            int temp = Integer.parseInt(cardNumberWithoutCheckDigit.substring(i, i + 1));
            if (alternate) {
                temp *= 2;
                if (temp > 9) {
                    temp -= 9;
                }
            }
            sum += temp;
            alternate = ! alternate;
        }

        return (10 - (sum % 10)) % 10;
    }

    private boolean isCardNumberInDatabase(String cardNumber) {
        return orderDao.findByCardNumber(cardNumber).isPresent();
    }
}
