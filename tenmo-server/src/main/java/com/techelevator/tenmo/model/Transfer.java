package com.techelevator.tenmo.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Transfer {
    private int transferId;
    private int senderAccountId;
    private int receiverAccountId;
    private Timestamp timestamp;
    private double amount;
    private TransferStatus status;

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(int senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public int getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(int recipientAccountId) {
        this.receiverAccountId = recipientAccountId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amountSent) {
        this.amount = amountSent;
    }

    public TransferStatus getStatus() {
        return status;
    }

    public void setStatus(TransferStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transfer_id=" + transferId +
                "sender_account_id" + senderAccountId +
                "receiver_account_id" + receiverAccountId +
                "transfer_timestamp" + timestamp +
                "amount" + amount +
                "status" + status +
                "}";
    }

}

