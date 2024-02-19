package ru.demo.repository;

import ru.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(Long id);
    Optional<User> findByName(String name);
    List<User> getAll();
    Optional<User> create(String name);
    Optional<User> update(Long id, String name);
    void delete(Long id);
}
