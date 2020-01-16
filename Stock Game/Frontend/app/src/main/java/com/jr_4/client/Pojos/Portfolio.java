package com.jr_4.client.Pojos;

public class Portfolio {

        private int stockId;
        private String stockName;
        private int quantity;
        private double currentValue;

    public Portfolio(String stockName, int quantity, double currentValue, int id){
        this.stockName= stockName;
        this.quantity= quantity;
        this.currentValue=currentValue;
        this.stockId=id;
    }

    public Portfolio(String stockName, int quantity, double currentValue) {
        this.stockName = stockName;
        this.quantity = quantity;
        this.currentValue = currentValue;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "stockId=" + stockId +
                ", stockName='" + stockName + '\'' +
                ", quantity=" + quantity +
                ", currentValue=" + currentValue +
                '}';
    }
}
