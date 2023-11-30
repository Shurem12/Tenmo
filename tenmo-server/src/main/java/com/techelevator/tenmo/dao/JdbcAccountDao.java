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

    public Account createAccount(int userId) {
        //TODO: check if user exists before creating account

        Account newAccount = null;

        String sql = "insert into account(user_id,balance)\n" +
                "values (?,?) RETURNING account_id ;";

        try {
            int accountId = jdbcTemplate.queryForObject(sql, int.class, userId,1000);
            newAccount = new Account();
            newAccount.setAccountId(accountId);
            newAccount.setUserId(userId);
            newAccount.setBalance(1000.000);

        } catch (Exception e) {
           throw new DaoException("Account not created");
        }

        return newAccount;
    }



    public  boolean deleteAccount(int userID, int  accountId){
        boolean successFul=false;

        String sql="delete \n" +
                "from account\n" +
                "where user_id=? and account_id=?;";

        try {
            jdbcTemplate.update(sql,userID,accountId);

            successFul=true;

        }catch(Exception ex){
            throw new DaoException("Account was not deleted");

        }
        return successFul;

    }




    public List<Account> listTransfers(int userId) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM account WHERE user_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql,userId);
            while (results.next()){
                accounts.add(mapRowToAccount(results));
            }
        } catch (Exception e) {
            throw new DaoException("Could not list accounts");

        }

        return accounts;
    }



    public  Account sendMoney ( int senderAccountId, int recipientAccountId, double amount){
        // TODO: verify sender account has enough money

//        Account senderAccount = null;
//        Account recipientAccount = null;

//        TODO: add begin and commit to the sql command
        String sqlUpdateSenderAccount="update account\n" +
                "set balance=(balance-?)\n" +
                "where account_id=?;";
        String sqlUpdateRecipientAccount="update account\n" +
                "set balance=(balance+?)\n" +
                "where account_id=?;";
        try{
            jdbcTemplate.update(sqlUpdateSenderAccount,senderAccountId,amount);
            jdbcTemplate.update(sqlUpdateRecipientAccount,recipientAccountId,amount);
        }catch (Exception ex){
            throw new DaoException("Could not send money");
        }


        // get balance from sender
        // if sender has enough money, send amount to recipient account

        return null;
    }


    public  Account retrieveDetails(int userId){
        String sql="select*\n" +
                "from account\n" +
                "where user_id=?;\n";
            //ToDo receive tranfer details based on tranfer_id. need table
        try {
          SqlRowSet rowSet=  jdbcTemplate.queryForRowSet(sql,userId);
            if (rowSet.next()){
                return mapRowToAccount(rowSet);
            }
        }catch (Exception ex){
            throw new DaoException("Could not retrieveDetails");

        }

        return null;
    }



    public Account mapRowToAccount(SqlRowSet result){
        Account account = new Account();
        account.setUserId(result.getInt("user_id"));
        account.setAccountId(result.getInt("account_id"));
        account.setBalance(result.getDouble("balance"));
        return account;
    }
}
