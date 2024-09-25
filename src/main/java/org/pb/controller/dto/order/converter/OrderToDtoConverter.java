package org.pb.controller.dto.order.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.pb.controller.dto.order.OrderDto;
import org.pb.model.order.Order;

public class OrderToDtoConverter implements Converter<Order, OrderDto> {
    @Override
    public OrderDto convert(MappingContext<Order, OrderDto> mc) {
        mc.getDestination().setId(mc.getSource().getId());
        mc.getDestination().setCreated(mc.getSource().getCreated());
        mc.getDestination().setFirstNameUA(mc.getSource().getClient().getFirstNameUa());
        mc.getDestination().setLastNameUA(mc.getSource().getClient().getLastNameUa());
        mc.getDestination().setFirstNameEN(mc.getSource().getClient().getFirstNameEn());
        mc.getDestination().setLastNameEN(mc.getSource().getClient().getLastNameEn());
        mc.getDestination().setOperatorFullName(mc.getSource().getOperator().getFullName());
        mc.getDestination().setCardType(mc.getSource().getCardType());
        mc.getDestination().setCardNumber(mc.getSource().getCardNumber());
        return mc.getDestination();
    }
}
