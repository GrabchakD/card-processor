package org.pb.service;

import org.pb.controller.dto.client.ClientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface ClientService {

    Page<ClientDto> getAll(Pageable pageable);

    ClientDto getById(Long id);

    ClientDto create(ClientDto clientDto);

    ClientDto update(Long id, ClientDto clientDto, Principal principal);

    Long countAllClients();
}
