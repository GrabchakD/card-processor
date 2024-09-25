package org.pb.service.impl;

import lombok.RequiredArgsConstructor;
import org.pb.controller.exception.LoginException;
import org.pb.dao.UserDao;
import org.pb.model.user.Role;
import org.pb.model.user.User;
import org.pb.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String NOT_DEFINED = "NOT_DEFINED";
    private static final Logger logger = LoggerFactory.getLogger(StatisticServiceImpl.class);

    private final UserDao userDao;


    @Override
    public User findByLogin(String login) {
        logger.info(String.format("Getting user by login: %s process", login));
        return userDao.findByLogin(login)
                .orElseThrow(() -> new LoginException(login));
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        logger.info(String.format("Load user by username: %s process", login));
        User user = userDao.findByLogin(login)
                .orElseThrow(() -> new LoginException(login));

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(Role::getRoleName)
                .map(SimpleGrantedAuthority::new)
                .collect(toList());

        return new org.springframework.security.core.userdetails.User(user.getLogin(), verifyUserPassword(user), authorities);
    }

    @Override
    public List<User> getOperatorsWithOrdersInPeriod(LocalDate from, LocalDate to) {
        logger.info(String.format("Getting operators with orders in period from: %s to: %s", from, to));
        return userDao.findAllWithOrdersFromCurrentMonth(from, to);
    }

    public static String verifyUserPassword(User user) {
        logger.info("Verifying user password");
        return user.getPassword() != null ? user.getPassword() : NOT_DEFINED;
    }
}
