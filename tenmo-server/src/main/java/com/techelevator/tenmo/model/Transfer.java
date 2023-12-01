package com.techelevator.tenmo.model;

public class Transfer {
    private int transferId;
    private int senderUserId;
    private int senderAccountId;
    private int recipientUserId;
    private int recipientAccountId;
    private int transferTimestamp;
    private int amountSent;
    private int status;

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(int senderUserId) {
        this.senderUserId = senderUserId;
    }

    public int getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(int senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public int getRecipientUserId() {
        return recipientUserId;
    }

    public void setRecipientUserId(int recipientUserId) {
        this.recipientUserId = recipientUserId;
    }

    public int getRecipientAccountId() {
        return recipientAccountId;
    }

    public void setRecipientAccountId(int recipientAccountId) {
        this.recipientAccountId = recipientAccountId;
    }

    public int getTransferTimestamp() {
        return transferTimestamp;
    }

    public void setTransferTimestamp(int transferTimestamp) {
        this.transferTimestamp = transferTimestamp;
    }

    public int getAmountSent() {
        return amountSent;
    }

    public void setAmountSent(int amountSent) {
        this.amountSent = amountSent;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
