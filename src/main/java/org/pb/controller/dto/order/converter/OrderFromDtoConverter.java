package org.pb.controller.dto.order.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.pb.controller.dto.order.OrderDto;
import org.pb.model.order.Order;

public class OrderFromDtoConverter implements Converter<OrderDto, Order> {
    @Override
    public Order convert(MappingContext<OrderDto, Order> mc) {
        mc.getDestination().setId(mc.getSource().getId());
        mc.getDestination().setCreated(mc.getSource().getCreated());
        mc.getDestination().setCardType(mc.getSource().getCardType());
        mc.getDestination().setCardNumber(mc.getSource().getCardNumber());
        return mc.getDestination();
    }
}
