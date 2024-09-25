package org.pb.dao;

import org.pb.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

    @Query(nativeQuery = true, value = "select distinct u.id, u.login, u.password, u.full_name from users u " +
            "join orders o on u.id = fk_operator_id " +
            "where o.created between :from and :to " +
            "group by u.id " +
            "order by count(o.id) desc, u.full_name asc ")
    List<User> findAllWithOrdersFromCurrentMonth(@Param("from") LocalDate from, @Param("to") LocalDate to);
}
