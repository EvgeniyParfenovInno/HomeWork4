package ru.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import ru.demo.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final String USERNAME_FIELD = "username";
    private static final String ID_FIELD = "id";
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> findById(Long id) throws SQLException {
        String sql = "select * from users where id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new User()
                        .setId(rs.getLong(ID_FIELD))
                        .setUserName(rs.getString(USERNAME_FIELD))
                );
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByName(String name) throws SQLException {
        String sql = "select * from users where username=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new User()
                        .setId(rs.getLong(ID_FIELD))
                        .setUserName(rs.getString(USERNAME_FIELD))
                );
            }
            return Optional.empty();
        }
    }

    @Override
    public List<User> getAll() throws SQLException {
        String sql = "select * from users";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            List<User> users = new LinkedList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new User()
                        .setId(rs.getLong(ID_FIELD))
                        .setUserName(rs.getString(USERNAME_FIELD)));
            }
            return users;
        }
    }

    @Override
    public Optional<User> create(String name) throws SQLException {
        String sql = "insert into users (username) values(?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
            return findByName(name);
        }
    }

    @Override
    public Optional<User> update(Long id, String name) throws SQLException {
        String sql = "update users set username=? where id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setLong(2, id);
            ps.executeUpdate();
            return findById(id);
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "delete from users where Id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public void clear() throws SQLException {
        String sql = "delete from users";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }
}
