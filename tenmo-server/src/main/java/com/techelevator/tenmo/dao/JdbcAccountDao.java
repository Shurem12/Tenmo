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
    public Account findById(int accountId) {
        String sql = "select * from account where account_id = ?;";

        Account account = null;
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId);
            if (result.next())
                account = mapRowToAccount(result);
        } catch (Exception e) {
            throw new DaoException("Account was not found");
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
            jdbcTemplate.update(sql, amount, account.getAccountId());
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

//    @Override
//    public void delete(int userId) {
//        String sql = "delete \n" +
//                "from account\n" +
//                "where user_id=?;";
//        try {
//            jdbcTemplate.update(sql, userId);
//        } catch (Exception ex) {
//            throw new DaoException("Account was not deleted");
//        }
//    }

//    @Override
//    public boolean hasAccount(int userId){
//        String sql = "select * from account where user_id = ?";
//
//        try {
//            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
//            if (result.next()) {
//                return true;
//            }
//        } catch (Exception e) {
//            throw new DaoException("Account was not found");
//        }
//        return false;
//    }

//    @Override
//    public Account findByUserId(int userId) {
//        Account account = null;
//        String sql = "select * from account where user_id = ?";
//
//        try {
//            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
//            if (result.next()) {
//                account = mapRowToAccount(result);
//            }
//        } catch (Exception e) {
//            throw new DaoException("Account was not found");
//        }
//        return account;
//    }


//    @Override
//    public boolean sendMoney(int senderAccountId, int recipientAccountId, double amount) {
//        // TODO: verify sender account has enough money
//        boolean status = false;
//
//        String sqlUpdateSenderAccount = "update account " +
//                "set balance = (balance - ?) " +
//                "where account_id = ?;";
//
//        String sqlUpdateRecipientAccount = "update account " +
//                "set balance = (balance + ?) " +
//                "where account_id = ?;";
//
//        try {
//            jdbcTemplate.update(sqlUpdateSenderAccount, amount, senderAccountId);
//            jdbcTemplate.update(sqlUpdateRecipientAccount, amount, recipientAccountId);
//            status = true;
//        } catch (Exception ex) {
//            throw new DaoException("Could not send money");
//        }
//        return status;
//    }

}
