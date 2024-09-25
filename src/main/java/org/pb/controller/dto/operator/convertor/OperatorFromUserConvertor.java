package org.pb.controller.dto.operator.convertor;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.pb.controller.dto.operator.OperatorDto;
import org.pb.model.user.User;

public class OperatorFromUserConvertor implements Converter<User, OperatorDto> {
    @Override
    public OperatorDto convert(MappingContext<User, OperatorDto> mc) {
        mc.getDestination().setLogin(mc.getSource().getLogin());
        mc.getDestination().setFullName(mc.getSource().getFullName());
        mc.getDestination().setOrdersCount(mc.getSource().getOrders().size());
        return mc.getDestination();
    }
}
