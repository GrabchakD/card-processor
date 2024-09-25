package org.pb.dao;

import org.pb.model.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardDao extends JpaRepository<Card, Long> {
    List<Card> findAllByOrderByIdDesc();

    Optional<Card> findByType(String type);

    @Query(nativeQuery = true, value = "select c.type from cards c")
    List<String> findAllCardTypes();
}
