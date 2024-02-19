package ru.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.demo.config.DataSourceConfig;
import ru.demo.config.UserConfig;
import ru.demo.service.UserService;

public class App {
    public static void main(String[] args) {
        System.out.println("fix by https://www.slf4j.org/codes.html#StaticLoggerBinder");
        ApplicationContext context = new AnnotationConfigApplicationContext(UserConfig.class, DataSourceConfig.class);
        UserService userService = context.getBean(UserService.class);
        userService.delete(1L);
        //userService.delete(" 1");
        System.out.println("ok");
    }
}
