package org.pb.controller.dto.client.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.pb.controller.dto.client.ClientDto;
import org.pb.model.client.Client;

public class ClientFromDtoConverter implements Converter<ClientDto, Client> {
    @Override
    public Client convert(MappingContext<ClientDto, Client> mc) {
        mc.getDestination().setId(mc.getSource().getId());
        mc.getDestination().setFirstNameUa(mc.getSource().getFirstNameUa());
        mc.getDestination().setLastNameUa(mc.getSource().getLastNameUa());
        mc.getDestination().setFirstNameEn(mc.getSource().getFirstNameEn());
        mc.getDestination().setLastNameEn(mc.getSource().getLastNameEn());
        mc.getDestination().setBirth(mc.getSource().getBirth());
        mc.getDestination().setPhone(mc.getSource().getPhone());
        mc.getDestination().setEmail(mc.getSource().getEmail());
        mc.getDestination().setOperatorFullName(mc.getSource().getOperatorFullName());
        return mc.getDestination();
    }
}
