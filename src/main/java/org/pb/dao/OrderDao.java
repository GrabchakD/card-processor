package org.pb.dao;

import org.pb.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {

    List<Order> findAllByOrderByIdDesc();

    List<Order> findAllByCardNumberIsNull();

    Optional<Order> findByCardNumber(String cardNumber);
}
