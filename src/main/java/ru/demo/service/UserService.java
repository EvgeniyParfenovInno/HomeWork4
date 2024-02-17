package ru.demo.service;

import ru.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id);
    Optional<User> findByName(String name);
    List<User> getAll();
    void create(User user);
    void update(User user);
    void delete(User user);
    void delete(Long id);
}
