package org.pb.controller;

import lombok.RequiredArgsConstructor;
import org.pb.controller.dto.card.CardDto;
import org.pb.controller.dto.common.PageableExt;
import org.pb.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
    private static final Logger logger = LoggerFactory.getLogger(CardController.class);

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<PageableExt<CardDto>> getAll(Pageable pageable) {
        logger.info("Attempt to get all cards");
        return PageableExt.ofResponse(cardService.getAll(pageable));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/types")
    public ResponseEntity<List<String>> getAllCardsType() {
        logger.info("Attempt to get all cards type");
        return ResponseEntity.ok(cardService.getAllCardsType());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<CardDto> getById(@PathVariable Long id) {
        logger.info(String.format("Attempt to get cards by id: %s", id));
        return ResponseEntity.ok(cardService.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CardDto> create(@RequestBody CardDto cardDto) {
        logger.info(String.format("Attempt to create cards: $s", cardDto.toString()));
        return ResponseEntity.ok(cardService.create(cardDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<CardDto> update(@PathVariable Long id, @RequestBody CardDto cardDto) {
        logger.info(String.format("Attempt to update cards: %s, with id: %s", cardDto, id));
        return ResponseEntity.ok(cardService.update(id, cardDto));
    }
}
