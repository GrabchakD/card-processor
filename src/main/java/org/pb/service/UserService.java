package org.pb.service;

import org.pb.model.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.util.List;

public interface UserService extends UserDetailsService {

    User findByLogin(String login);
    List<User> getOperatorsWithOrdersInPeriod(LocalDate from, LocalDate to);
}
