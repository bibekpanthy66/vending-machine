package com.example.vendingmachine;

public class MoneyLogsDto {
    private String id,type,amount,balance;

    public MoneyLogsDto(String id, String type, String amount, String balance) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
