package com.example.vendingmachine;

public class ItemLogsDto {
    private String id,value,name,price,quantityFrom,quantityTo,balanceFrom,balanceTo;

    public ItemLogsDto(String id, String value, String name, String price, String quantityFrom, String quantityTo, String balanceFrom, String balanceTo) {
        this.id = id;
        this.value = value;
        this.name = name;
        this.price = price;
        this.quantityFrom = quantityFrom;
        this.quantityTo = quantityTo;
        this.balanceFrom = balanceFrom;
        this.balanceTo = balanceTo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantityFrom() {
        return quantityFrom;
    }

    public void setQuantityFrom(String quantityFrom) {
        this.quantityFrom = quantityFrom;
    }

    public String getQuantityTo() {
        return quantityTo;
    }

    public void setQuantityTo(String quantityTo) {
        this.quantityTo = quantityTo;
    }

    public String getBalanceFrom() {
        return balanceFrom;
    }

    public void setBalanceFrom(String balanceFrom) {
        this.balanceFrom = balanceFrom;
    }

    public String getBalanceTo() {
        return balanceTo;
    }

    public void setBalanceTo(String balanceTo) {
        this.balanceTo = balanceTo;
    }
}
