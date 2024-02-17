package ru.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.demo.config.UserConfig;
import ru.demo.service.UserService;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(UserConfig.class);
        UserService userService = context.getBean(UserService.class);
        userService.delete(1L);
        System.out.println("ok");
    }
}
