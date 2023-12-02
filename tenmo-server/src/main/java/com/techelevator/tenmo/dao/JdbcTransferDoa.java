package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDoa {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Transfer addTransfer(int senderUserId,int senderAccountId,int recipientUserId,int recipientAccountId,int amountSent,String status){
        Transfer transfer=null;
        String sql="insert into transfer(sender_user_id,sender_account_id,recipient_user_id,recipient_account_id,transfer_timestamp,amount_sent,transfer_status)\n" +
                "values (?,?,?,?,CURRENT_TIME,?,?);";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, senderUserId,senderAccountId,recipientUserId,recipientAccountId,amountSent,status);
           while (results.next()) {
//               transfer.add(mapRowToAccount(results));
           }

         } catch (Exception e) {
           throw new DaoException("Could not list accounts");
        }

        return null;
    }

    public Transfer getTransferById(int transferId) {
        Transfer transfer = null;
        String sql = "select * from transfer where transfer_id = ?;";

        return transfer;
    }

    public List<Transfer> listTransfersByUserId(int userId) {
        List<Transfer> listOfTransfers = new ArrayList<>();
        String sql = "SELECT * FROM transfer WHERE user_id = ?;";

        return listOfTransfers;
    }

    public List<Transfer> listTransfersByAccountId(int accountId) {
        List<Transfer> listOfTransfers = new ArrayList<>();
        String sql = "SELECT * FROM account WHERE user_id = ?;";

//        try {
//            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
//            while (results.next()) {
//                accounts.add(mapRowToAccount(results));
//            }
//        } catch (Exception e) {
//            throw new DaoException("Could not list accounts");
//        }

        return listOfTransfers;
    }

    public Transfer mapTransferToRow(SqlRowSet result){
        Transfer transfer = new Transfer();
        transfer.setTransferId(result.get(""));
        transfer.setSenderUserId(result.get(""));
        transfer.setSenderAccountId(result.get(""));
        transfer.setRecipientUserId(result.get(""));
        transfer.setRecipientAccountId(result.get(""));
        transfer.setTransferTimestamp(result.get(""));
        transfer.setAmountSent(result.get(""));
        transfer.setStatus(result.get(""));
        return transfer;
    }
}
