package org.pb.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.pb.controller.dto.card.CardDto;
import org.pb.dao.CardDao;
import org.pb.model.card.Card;
import org.pb.service.CardService;
import org.pb.utils.PageCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.pb.utils.BaseUtils.map;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardDao cardDao;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);

    @Override
    public Page<CardDto> getAll(Pageable pageable) {
        logger.info("Getting all cards process");
        List<CardDto> cards = cardDao.findAllByOrderByIdDesc().stream()
                .map(card -> map(card, CardDto.class).apply(modelMapper))
                .collect(toList());

        return new PageCreator<>(cards, pageable).pageFromList();
    }

    @Override
    public List<String> getAllCardsType() {
        logger.info("Getting all card types process");
        return cardDao.findAllCardTypes();
    }

    @Override
    public CardDto getById(Long id) {
        logger.info(String.format("Getting card by id: %s process", id));
        return cardDao.findById(id).stream()
                .map(card -> map(card, CardDto.class).apply(modelMapper))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(String.format("Card with id %s not found", id)));
    }

    @Override
    public CardDto create(CardDto cardDto) {
        logger.info(String.format("Create card: %s process", cardDto));
        cardDto.setType(cardDto.getType().toUpperCase());
        Card card = cardDao.save(map(cardDto, Card.class).apply(modelMapper));
        return map(card, CardDto.class).apply(modelMapper);
    }

    @Override
    public CardDto update(Long id, CardDto cardDto) {
        logger.info(String.format("Update card: %s process", cardDto));
        return cardDao.findById(id).stream()
                .peek(card -> {
                    modelMapper.map(cardDto, card);
                    card.setType(card.getType().toUpperCase());
                    card.setId(id);
                })
                .map(cardDao::save)
                .map(card -> map(card, CardDto.class).apply(modelMapper))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(String.format("Card with id %s not found", id)));
    }

    @Override
    public Card findBinByCardType(String cardType) {
        logger.info(String.format("Find BIN by card type: %s process", cardType));
        return cardDao.findByType(cardType)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Card with type %s not found", cardType)));
    }
}
