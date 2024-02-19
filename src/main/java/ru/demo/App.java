package ru.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.demo.config.DataSourceConfig;
import ru.demo.config.UserConfig;
import ru.demo.exception.AlreadyExistsException;
import ru.demo.exception.NotExistsException;
import ru.demo.model.User;
import ru.demo.service.UserService;

import java.sql.SQLException;
import java.util.Optional;

@Slf4j
public class App {

    private static final String USER_INFO = " - пользователь: {}";
    private static final String CATCH_MSG = "Перехвачено: {}";

    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(UserConfig.class, DataSourceConfig.class);
        UserService userService = context.getBean(UserService.class);

        Optional<User> user;

        log.info("");
        log.info("Очищение таблицы...");
        userService.clear();
        log.info("OK");
        log.info("Создание пользователей...");
        userService.create(UserName.IVAN.name());
        userService.create(UserName.JOHN.name());
        userService.create(UserName.BOB.name());
        log.info("OK");
        log.info("");

        log.info("Получение списка всех пользователей...");
        userService.getAll().forEach(u -> log.info("- пользователь: {}", u));
        log.info("");

        log.info("Получение пользователя по имени {}...", UserName.JOHN.name());
        user = userService.findByName(UserName.JOHN.name());
        log.info(USER_INFO, user.orElse(null));
        log.info("");

        log.info("Получение пользователя по идентификатору {}...", user.map(User::getId).orElse(0L));
        user = userService.findById(user.map(User::getId).orElse(0L));
        log.info(USER_INFO, user.orElse(null));
        log.info("");

        log.info("Переименование пользователя {}...", user.orElse(null));
        user = userService.update(user.map(User::getId).orElse(0L), UserName.TOM.name());
        log.info(USER_INFO, user.orElse(null));
        log.info("");

        log.info("Удаление пользователя {}...", user.orElse(null));
        userService.delete(user.map(User::getId).orElse(0L));
        log.info("OK");
        log.info("");

        log.info("Получение списка всех пользователей...");
        userService.getAll().forEach(u -> log.info("- пользователь: {}", u));
        log.info("");


        try {
            log.info("Удаление несуществующего пользователя {}...", user.orElse(null));
            userService.delete(user.map(User::getId).orElse(0L));
        } catch (NotExistsException e) {
            log.error(CATCH_MSG, e.getMessage());
        }

        try {
            log.info("");
            log.info("Создание пользователя с уже существующим именем {}...", UserName.IVAN.name());
            userService.create(UserName.IVAN.name());
        } catch (AlreadyExistsException e) {
            log.error(CATCH_MSG, e.getMessage());
        }

        try {
            log.info("");
            user = userService.findByName(UserName.BOB.name());
            log.info("Переименование пользователя {} в уже существующее имя {}...", user.orElse(null), UserName.IVAN.name());
            userService.update(user.map(User::getId).orElse(0L), UserName.IVAN.name());
        } catch (AlreadyExistsException e) {
            log.error(CATCH_MSG, e.getMessage());
        }

        log.info("ok");
    }

    private enum UserName {
        IVAN, JOHN, BOB, TOM;
    }
}
