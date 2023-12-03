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

    public Transfer addTransfer(int senderAccountId, int recipientAccountId, int amountSent, String status) {
        Transfer transfer = null;
        String sql = "insert into transfer(sender_account_id, recipient_account_id, transfer_timestamp, transfer_amount)\n" +
                "values (?,?,CURRENT_TIME,?,?);";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, senderAccountId, recipientAccountId, amountSent);
            while (results.next()) {
//               transfer.add(mapRowToAccount(results));
            }

        } catch (Exception e) {
            throw new DaoException("Could not list accounts");
        }

        return null;
    }

    public Transfer findById(int transferId) {
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
        String sql = "SELECT * FROM account WHERE user_id = ?;";

        List<Transfer> listOfTransfers = new ArrayList<>();
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

    public Transfer mapTransferToRow(SqlRowSet result) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(result.getInt("transfer_id"));
        transfer.setSenderAccountId(result.getInt("sender_account_id"));
        transfer.setRecipientAccountId(result.getInt("recipient_account_id"));
        transfer.setTimestamp(result.getTimestamp("transfer_timestamp"));
        transfer.setAmount(result.getDouble("amount"));
        transfer.setStatus(result.getString("status"));
        return transfer;
    }
}
