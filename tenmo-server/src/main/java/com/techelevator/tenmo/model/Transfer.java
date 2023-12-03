package com.techelevator.tenmo.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

public class Transfer {
    private int transferId;
    private int senderAccountId;
    private int recipientAccountId;
    private Timestamp timestamp;
    private double amount;
    private String status;

    public Transfer() {
    }

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

    public int getRecipientAccountId() {
        return recipientAccountId;
    }

    public void setRecipientAccountId(int recipientAccountId) {
        this.recipientAccountId = recipientAccountId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transfer_id=" + transferId +
                "sender_account_id" + senderAccountId +
                "recipient_account_id" + recipientAccountId +
                "transfer_timestamp" + timestamp +
                "amount" + amount +
                "status" + status +
                "}";
    }

}

