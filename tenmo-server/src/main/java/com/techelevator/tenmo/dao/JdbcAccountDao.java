package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcAccountDao implements AccountDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int create(int userId) {
        String sql = "insert into account(user_id) " +
                "values (?) returning account_id;";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
            if (result.next()) {
                return result.getInt("account_id");
            } else {
                throw new DaoException("Account not created");
            }
        } catch (Exception e) {
            throw new DaoException("Account not created");
        }
    }

    @Override
    public Account findByAccountId(int accountId) {
        String sql = "select * from account where account_id = ?;";

        Account account = null;
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId);
            if (result.next())
                account = mapRowToAccount(result);
        } catch (Exception e) {
            String message = String.format("Account was not found for account_id(%s)", accountId);
            throw new DaoException(message);
        }
        return account;
    }

    @Override
    public Account findByUserId(int userId) {
        String sql = "select * from account where user_id = ?;";

        Account account = null;
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
            if (result.next())
                account = mapRowToAccount(result);
        } catch (Exception e) {
            String message = String.format("Account was not found for user_id(%s)", userId);
            throw new DaoException(message);
        }
        return account;
    }
    @Override
    public Account findByUsername(String username){
        String sql = "select * from account join tenmo_user on account.user_id = tenmo_user.user_id where username = ?;";

        Account account = null;
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
            if (result.next())
                account = mapRowToAccount(result);
        } catch (Exception e) {
            String message = String.format("Account was not found for username(%s)", username);
            throw new DaoException(message);
        }
        return account;
    }

    @Override
    public double getBalanceById(int accountId) {
        String sql = "select balance from account where user_id = ?;";

        try {
            return jdbcTemplate.queryForObject(sql, double.class, accountId);
        } catch (Exception ex) {
            String message = String.format("Balance not found for account_id(%s)", accountId);
            throw new DaoException(message);
        }
    }

    @Override
    public void deposit(Account account, double amount) {
        String sql = "update account set balance = (balance + ?) where account_id = ?;";

        try {
            jdbcTemplate.update(sql, amount, account.getAccountId());
        } catch (Exception e) {
            String message = String.format("Failed to deposit amount(%s) to account_id(%s)", amount, account.getAccountId());
            throw new DaoException(message);
        }
    }

    @Override
    public void withdraw(Account account, double amount) {
        String sql = "update account set balance = (balance - ?) where account_id = ?;";

        try {
            if (amount > 0 && account.getBalance() > 0 && account.getBalance() >= amount) {
                jdbcTemplate.update(sql, amount, account.getAccountId());
            }
        } catch (Exception e) {
            String message = String.format("Failed to deposit amount(%s) to account_id(%s)", amount, account.getAccountId());
            throw new DaoException(message);
        }
    }

    public Account mapRowToAccount(SqlRowSet result) {
        Account account = new Account();
        account.setUserId(result.getInt("user_id"));
        account.setAccountId(result.getInt("account_id"));
        account.setBalance(result.getDouble("balance"));
        return account;
    }
}
