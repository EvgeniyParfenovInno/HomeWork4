package ru.demo.service;

import ru.demo.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id) throws SQLException;
    Optional<User> findByName(String name) throws SQLException;
    List<User> getAll() throws SQLException;
    Optional<User> create(String name) throws SQLException;
    Optional<User> update(Long id, String name) throws SQLException;
    void delete(String name) throws SQLException;
    void delete(Long id) throws SQLException;
    void clear() throws SQLException;
}
