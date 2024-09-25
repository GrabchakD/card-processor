package org.pb.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.pb.controller.dto.client.ClientDto;
import org.pb.dao.ClientDao;
import org.pb.model.client.Client;
import org.pb.service.ClientService;
import org.pb.utils.PageCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.pb.utils.BaseUtils.map;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientDao clientDao;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Override
    public Page<ClientDto> getAll(Pageable pageable) {
        logger.info("Getting all clients process");
        List<ClientDto> clients = clientDao.findAllByOrderByIdDesc().stream()
                .map(client -> map(client, ClientDto.class).apply(modelMapper))
                .collect(toList());

        return new PageCreator<>(clients, pageable).pageFromList();
    }

    @Override
    public ClientDto getById(Long id) {
        logger.info(String.format("Getting clients by id: %s process", id));
        return clientDao.findById(id).stream()
                .map(client -> map(client, ClientDto.class).apply(modelMapper))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(String.format("Client with id %s not found", id)));
    }

    @Override
    public ClientDto create(ClientDto clientDto) {
        logger.info(String.format("Create client: %s process", clientDto));
        Client client = clientDao.save(map(clientDto, Client.class).apply(modelMapper));
        return map(client, ClientDto.class).apply(modelMapper);
    }

    @Override
    public ClientDto update(Long id, ClientDto clientDto, Principal principal) {
        logger.info(String.format("Update client: %s process", clientDto));
        return clientDao.findById(id).stream()
                .peek(client -> {
                    modelMapper.map(clientDto, client);
                    client.setId(id);
                })
                .map(clientDao::save)
                .map(client -> map(client, ClientDto.class).apply(modelMapper))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(String.format("Client with id %s not found", id)));
    }

    @Override
    public Long countAllClients() {
        logger.info("Count all client process");
        return clientDao.count();
    }
}
