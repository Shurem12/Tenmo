package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDoa {
    @Autowired
    private JdbcTemplate jdbcTemplate;

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
}
