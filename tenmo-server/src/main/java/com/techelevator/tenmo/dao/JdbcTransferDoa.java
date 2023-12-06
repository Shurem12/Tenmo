package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDoa implements TransferDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcAccountDao accountDao;

    @Override
    public Transfer findByTransferId(int transferId) {
        Transfer transfer = null;
        String sql = "select * from transfer where transfer_id = ?;";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
            if (result.next())
                transfer = mapTransferToRow(result);
        } catch (Exception e) {
            String message = String.format("Transfer was not found for transfer_id(%s)", transferId);
            throw new DaoException(message);
        }
        return transfer;
    }

    @Override
    public Transfer init(Account sender, Account receiver, double amount) {
        String sql = "insert into transfer (sender_account_id, receiver_account_id, amount)\n" +
                "values (?, ?, ?) returning transfer_id";

        try {
            int senderAccountId = sender.getAccountId();
            int receiverAccountId = receiver.getAccountId();
            if (senderAccountId == receiverAccountId) {
                String message = String.format("Transactions must include (2) different users: user_id(%s)", receiverAccountId);
                throw new DaoException(message);
            }
//            TODO how to return Transfer object instead of transferID
            int transferId = jdbcTemplate.queryForObject(sql, int.class, senderAccountId, receiverAccountId, amount);
            return findByTransferId(transferId);
        } catch (Exception e) {
            String message = "Transfer was not created";
            throw new DaoException(message);
        }
    }

    public Transfer update(String sql, Transfer transfer) {
        Transfer updatedTransfer = null;
        try {
            int transferId = transfer.getTransferId();
            jdbcTemplate.update(sql, transferId);
            updatedTransfer = findByTransferId(transferId);
        } catch (Exception e) {
            String message = "Transfer was not completed";
            throw new DaoException(message);
        }
        return updatedTransfer;
    }

    public Transfer approve(Transfer transfer) {
        String sql = "update transfer set status = 'APPROVED', transfer_timestamp = CURRENT_TIMESTAMP where transfer_id = ?;";
        return update(sql, transfer);
    }

    public Transfer reject(Transfer transfer) {
        String sql = "update transfer set status = 'REJECTED', transfer_timestamp = CURRENT_TIMESTAMP where transfer_id = ?;";
        return update(sql, transfer);
    }

    public Transfer cancel(Transfer transfer) {
        String sql = "update transfer set status = 'CANCELLED', transfer_timestamp = CURRENT_TIMESTAMP where transfer_id = ?;";
        return update(sql, transfer);
    }

    public Transfer send(Account sender, Account receiver, double amount) {
        Transfer transfer = init(sender, receiver, amount);
        approve(transfer);

        if (sender.getBalance() >= amount && transfer.getStatus() == TransferStatus.APPROVED) {
            accountDao.withdraw(sender, amount);
            accountDao.deposit(receiver, amount);
        }
        return findByTransferId(transfer.getTransferId());
    }

    public List<Transfer> findAllByAccountId(int accountId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT * FROM transfer WHERE sender_account_id = ? or receiver_account_id = ?;";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId, accountId);
            while (result.next())
                transfers.add(mapTransferToRow(result));
        } catch (Exception e) {
            String message = String.format("Transfers not found for sender_account_id(%s) or receiver_account_id(%s)", accountId);
            throw new DaoException(message);
        }

        return transfers;
    }
    public List<Transfer> findBySenderAccountId(int accountId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT * FROM transfer WHERE sender_account_id = ?;";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId);
            while (result.next())
                transfers.add(mapTransferToRow(result));
        } catch (Exception e) {
            String message = String.format("Transfers not found for sender_account_id(%s)", accountId);
            throw new DaoException(message);
        }

        return transfers;
    }
    public List<Transfer> findByReceiverAccountId(int accountId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT * FROM transfer WHERE receiver_account_id = ?;";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId);
            while (result.next())
                transfers.add(mapTransferToRow(result));
        } catch (Exception e) {
            String message = String.format("Transfers not found for receiver_account_id(%s)", accountId);
            throw new DaoException(message);
        }

        return transfers;
    }

    public Transfer mapTransferToRow(SqlRowSet result) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(result.getInt("transfer_id"));
        transfer.setSenderAccountId(result.getInt("sender_account_id"));
        transfer.setReceiverAccountId(result.getInt("receiver_account_id"));
        transfer.setTimestamp(result.getTimestamp("transfer_timestamp"));
        transfer.setAmount(result.getDouble("amount"));
        transfer.setStatus(TransferStatus.valueOf(result.getString("status")));
        return transfer;
    }
}
