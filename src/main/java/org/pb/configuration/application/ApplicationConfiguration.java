package org.pb.configuration.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Configuration
public class ApplicationConfiguration {

    @Bean
    @Scope(scopeName = SCOPE_PROTOTYPE)
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
