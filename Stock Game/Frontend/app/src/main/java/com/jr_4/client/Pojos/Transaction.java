package com.jr_4.client.Pojos;

public class Transaction {
    private int id, user_id, quantity;
    private String symbol, date;
    private boolean buy;
    private double price;

    public Transaction(){

    }

    public Transaction(int id, int user_id, int quantity, String symbol, String date, boolean buy, double price) {
        this.id = id;
        this.user_id = user_id;
        this.quantity = quantity;
        this.symbol = symbol;
        this.date = date;
        this.buy = buy;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
