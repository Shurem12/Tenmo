package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcUserDao implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcAccountDao accountDao;

    @Override
    public boolean create(String username, String password) {
        String sql = "INSERT INTO tenmo_user (username, password_hash) VALUES (?, ?) RETURNING user_id";
        String password_hash = new BCryptPasswordEncoder().encode(password);

        int newUserId;
        try {
            newUserId = jdbcTemplate.queryForObject(sql, Integer.class, username, password_hash);
        } catch (DataAccessException e) {
            return false;
        }

//        Creates a new account with initial balance of $1000 when a new user is registered
        int accountId = accountDao.create(newUserId);
        accountDao.findByAccountId(accountId).setBalance(1000.00);

        return true;
    }

    public User current() {
        User user = null;
        return user;
    }

    @Override
    public boolean hasAccount(int userId) {
        String sql = "select * from account where user_id = ?";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
            if (result.next()) {
                return true;
            }
        } catch (Exception e) {
            throw new DaoException("Account was not found");
        }
        return false;
    }

    @Override
    public User findByUsername(String username) {
        User user = null;
        String sql = "select * from tenmo_user where username ilike ?;";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);

            if (result.next())
                user = mapRowToUser(result);

        } catch (Exception e) {
            String message = String.format("No data found: username(%s)", username);
            throw new DaoException(message);
        }
        return user;
    }

    @Override
    public User findById(int userId) {
        User user = null;
        String sql = "select * from tenmo_user where user_id = ?;";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);

            if (result.next())
                user = mapRowToUser(result);

        } catch (Exception e) {
            String message = String.format("No data found: user_id(%s)", userId);
            throw new DaoException(message);
        }
        return user;
    }

    @Override
    public List<String> findAll() {
        List<String> users = new ArrayList<>();
        String sql = "SELECT user_id, username, password_hash FROM tenmo_user;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            User user = mapRowToUser(results);
            users.add(user.getUsername());
        }
        return users;
    }


    private User mapRowToUser(SqlRowSet result) {
        User user = new User();
        user.setId(result.getInt("user_id"));
        user.setUsername(result.getString("username"));
        user.setPassword(result.getString("password_hash"));
        user.setActivated(true);
        user.setAuthorities("USER");
        return user;
    }
}
