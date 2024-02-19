package ru.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import ru.demo.model.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        return List.of();
    }

    @Override
    public Optional<User> create(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<User> update(Long id, String name) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
