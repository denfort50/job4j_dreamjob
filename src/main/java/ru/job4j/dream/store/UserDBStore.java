package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.dream.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.sql.*;

@Repository
public class UserDBStore {

    private static final String SELECT_ALL_USERS = "SELECT * FROM users ORDER BY id";
    private static final String INSERT_USER = "INSERT INTO users(email, password) VALUES (?, ?)";

    private static final Logger LOG = LogManager.getLogger(PostDBStore.class.getName());

    private final BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_ALL_USERS)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(createUser(it));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
        return users;
    }

    public Optional<User> add(User user) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.execute();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
                result = Optional.of(user);
            }
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
        return result;
    }

    private User createUser(ResultSet rs) throws SQLException {
        return new User(rs.getInt("id"),
                rs.getString("email"),
                rs.getString("password"));
    }
}
