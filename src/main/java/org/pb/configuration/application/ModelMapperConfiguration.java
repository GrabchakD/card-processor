package org.pb.configuration.application;

import org.modelmapper.ModelMapper;
import org.pb.controller.dto.client.converter.ClientFromDtoConverter;
import org.pb.controller.dto.client.converter.ClientToDtoConverter;
import org.pb.controller.dto.operator.convertor.OperatorFromUserConvertor;
import org.pb.controller.dto.order.converter.OrderFromDtoConverter;
import org.pb.controller.dto.order.converter.OrderToDtoConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.modelmapper.convention.MatchingStrategies.STRICT;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setMatchingStrategy(STRICT);

        var orderToDtoConverter = new OrderToDtoConverter();
        var orderFromDtoConverter = new OrderFromDtoConverter();
        var operatorFromUserConvertor = new OperatorFromUserConvertor();
        var clientFromDtoConvertor = new ClientFromDtoConverter();
        var clientToDtoConvertor = new ClientToDtoConverter();

        modelMapper.addConverter(orderToDtoConverter);
        modelMapper.addConverter(orderFromDtoConverter);
        modelMapper.addConverter(operatorFromUserConvertor);
        modelMapper.addConverter(clientFromDtoConvertor);
        modelMapper.addConverter(clientToDtoConvertor);

        return modelMapper;
    }
}
