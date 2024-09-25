package org.pb.controller;

import lombok.RequiredArgsConstructor;
import org.pb.controller.dto.client.ClientDto;
import org.pb.controller.dto.common.PageableExt;
import org.pb.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<PageableExt<ClientDto>> getAll(@PageableDefault Pageable pageable) {
        logger.info("Attempt to get all clients");
        return PageableExt.ofResponse(clientService.getAll(pageable));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDto> getById(@PathVariable Long id) {
        logger.info(String.format("Attempt to get client by id: %s", id));
        return ResponseEntity.ok(clientService.getById(id));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ClientDto> create(@RequestBody ClientDto clientDto) {
        logger.info(String.format("Attempt to create client: %s", clientDto));
        return ResponseEntity.ok(clientService.create(clientDto));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientDto> update(@PathVariable Long id, @RequestBody ClientDto clientDto, Principal principal) {
        logger.info(String.format("Attempt to update client: %s, with id: %s", clientDto, id));
        return ResponseEntity.ok(clientService.update(id, clientDto, principal));
    }
}
