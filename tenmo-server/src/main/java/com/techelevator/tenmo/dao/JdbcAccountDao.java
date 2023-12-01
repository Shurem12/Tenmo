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

    public Account getBalanceById(int accountId) {
        String sql = "select*\n" +
                "from account\n" +
                "where account_id=?;\n";
        //ToDo receive tranfer details based on tranfer_id. need table
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, accountId);
            if (rowSet.next()) {
                return mapRowToAccount(rowSet);
            }
        } catch (Exception ex) {
            throw new DaoException("Could not retrieveDetails");
        }
        return null;
    }

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

    public Account sendMoney(int senderAccountId, int recipientAccountId, double amount) {
        // TODO: verify sender account has enough money

//        TODO: add begin and commit to the sql command
        String sqlUpdateSenderAccount = "update account\n" +
                "set balance=(balance-?)\n" +
                "where account_id=?;";
        String sqlUpdateRecipientAccount = "update account\n" +
                "set balance=(balance+?)\n" +
                "where account_id=?;";
        try {
            jdbcTemplate.update(sqlUpdateSenderAccount, amount, senderAccountId);
            jdbcTemplate.update(sqlUpdateRecipientAccount, amount, recipientAccountId);
        } catch (Exception ex) {
            throw new DaoException("Could not send money");
        }
        // get balance from sender
        // if sender has enough money, send amount to recipient account

        return null;
    }


    public Account mapRowToAccount(SqlRowSet result) {
        Account account = new Account();
        account.setUserId(result.getInt("user_id"));
        account.setAccountId(result.getInt("account_id"));
        account.setBalance(result.getDouble("balance"));
        return account;
    }
}
