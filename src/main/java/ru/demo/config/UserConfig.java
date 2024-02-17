package ru.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.demo.service.UserService;
import ru.demo.service.UserServiceImpl;

@Configuration
public class UserConfig {
    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }
}
