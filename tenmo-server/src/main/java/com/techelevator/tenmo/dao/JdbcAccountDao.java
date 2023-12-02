package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public double getBalanceById(int accountId) {
        double balance = 0;

        String sql = "select*\n" +
                "from account\n" +
                "where account_id=?;\n";

        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, accountId);
            if (rowSet.next()) {
                Account account = mapRowToAccount(rowSet);
                balance = account.getBalance();
            }
        } catch (Exception ex) {
            throw new DaoException("Could not retrieveDetails");
        }
        return balance;

    }

    @Override
    public Account createAccount(int userId) {
        //TODO: check if user exists before creating account
        //ToDo prevent dublicates accounts

        Account newAccount = null;
        String sql = "insert into account(user_id,balance)\n" +
                "values (?,?) RETURNING account_id ;";
        try {
            int accountId = jdbcTemplate.queryForObject(sql, int.class, userId, 1000);
            newAccount = new Account();
            newAccount.setAccountId(accountId);
            newAccount.setUserId(userId);
            newAccount.setBalance(1000.000);

        } catch (Exception e) {
            throw new DaoException("Account not created");
        }
        return newAccount;
    }

    @Override
    public boolean deleteAccount(int userID, int accountId) {
        boolean successful = false;
        String sql = "delete \n" +
                "from account\n" +
                "where user_id=? and account_id=?;";
        try {
            jdbcTemplate.update(sql, userID, accountId);
            successful = true;
        } catch (Exception ex) {
            throw new DaoException("Account was not deleted");
        }
        return successful;
    }

    @Override
    public boolean sendMoney(int senderAccountId, int recipientAccountId, double amount) {
        // TODO: verify sender account has enough money
        boolean status = false;

        String sqlUpdateSenderAccount = "update account " +
                "set balance = (balance - ?) " +
                "where account_id = ?;";

        String sqlUpdateRecipientAccount = "update account " +
                "set balance = (balance + ?) " +
                "where account_id = ?;";

        try {
            jdbcTemplate.update(sqlUpdateSenderAccount, amount, senderAccountId);
            jdbcTemplate.update(sqlUpdateRecipientAccount, amount, recipientAccountId);
            status = true;
        } catch (Exception ex) {
            throw new DaoException("Could not send money");
        }
        return status;
    }


    public Account mapRowToAccount(SqlRowSet result) {
        Account account = new Account();
        account.setUserId(result.getInt("user_id"));
        account.setAccountId(result.getInt("account_id"));
        account.setBalance(result.getDouble("balance"));
        return account;
    }
}
