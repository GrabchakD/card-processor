package org.pb.service;

import org.pb.controller.dto.card.CardDto;
import org.pb.model.card.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CardService {

    Page<CardDto> getAll(Pageable pageable);

    List<String> getAllCardsType();

    CardDto getById(Long id);

    CardDto create(CardDto cardDto);

    CardDto update(Long id, CardDto cardDto);

    Card findBinByCardType(String cardType);
}
