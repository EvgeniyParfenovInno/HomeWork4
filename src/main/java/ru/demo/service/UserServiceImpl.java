package ru.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.demo.exception.AlreadyExistsException;
import ru.demo.exception.NotExistsException;
import ru.demo.model.User;
import ru.demo.repository.UserDao;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final String INVALID_NAME = "Не задано имя пользователя!";
    private static final String INVALID_ID = "Не задан идентификатор пользователя!";
    private static final String NOT_EXISTS_BY_ID = "Пользователь с идентификатором \"%s\" не существует!";
    private static final String NOT_EXISTS_BY_NAME = "Пользователь с именем \"%s\" не существует!";
    private static final String EXISTS_BY_NAME = "Пользователь с именем \"%s\" уже существует!";

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<User> findById(Long id) {
        if (id == null)
            throw new IllegalArgumentException(INVALID_ID);

        return userDao.findById(id);
    }

    @Override
    public Optional<User> findByName(String name) {
        if (!userNameIsValid(name))
            throw new IllegalArgumentException(INVALID_NAME);

        return userDao.findByName(name);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public Optional<User> create(String name) {
        if (!userNameIsValid(name))
            throw new IllegalArgumentException(INVALID_NAME);

        if (userDao.findByName(name).isPresent())
            throw new AlreadyExistsException(String.format(EXISTS_BY_NAME, name));

        return userDao.create(name);
    }

    @Override
    public Optional<User> update(Long id, String name) {
        if (id == null)
            throw new IllegalArgumentException(INVALID_ID);

        if (!userNameIsValid(name))
            throw new IllegalArgumentException(INVALID_NAME);

        var user = userDao.findById(id);
        if (!user.isPresent())
            throw new NotExistsException(String.format(NOT_EXISTS_BY_ID, id));

        if (user.map(User::getUserName).filter(s -> s.equals(name)).isPresent())
            return user;

        if (userDao.findByName(name).isPresent())
            throw new AlreadyExistsException(String.format(EXISTS_BY_NAME, name));

        return userDao.update(id, name);
    }

    @Override
    public void delete(String name) {
        if (!userNameIsValid(name))
            throw new IllegalArgumentException(INVALID_NAME);

        var user = userDao.findByName(name);
        if (!user.isPresent())
            throw new NotExistsException(String.format(NOT_EXISTS_BY_NAME, name));

        userDao.delete(user.map(User::getId).orElse(-1L));
    }

    @Override
    public void delete(Long id) {
        if (id == null)
            throw new IllegalArgumentException(INVALID_ID);

        if (!userDao.findById(id).isPresent())
            throw new NotExistsException(String.format(NOT_EXISTS_BY_ID, id));

        userDao.delete(id);
    }

    private static boolean userNameIsValid(String name) {
        return name != null && !"".equals(name.trim());
    }
}
