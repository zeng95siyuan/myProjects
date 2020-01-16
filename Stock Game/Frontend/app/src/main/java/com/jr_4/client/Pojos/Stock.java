package com.jr_4.client.Pojos;

public class Stock {
    private String symbol,companyName,primaryExchange;
    private double latestPrice, change, changePercent;

    public Stock() {

    }

    public Stock(String symbol, String companyName, String primaryExchange, double latestPrice, double change, double changePercent) {
        this.change = change;
        this.changePercent=changePercent;
        this.latestPrice= latestPrice;
        this.symbol=symbol;
        this.companyName=companyName;
        this.primaryExchange=primaryExchange;
    }

    public void setSymbol(String symbol){
        this.symbol=symbol;
    }

    public void setCompanyName(String companyName){
        this.companyName=companyName;
    }

    public void setPrimaryExchange(String primaryExchange){
        this.symbol=primaryExchange;
    }

    public void setChange(double change){
        this.change=change;
    }

    public void setChangePercent(double changePercent){
        this.changePercent=changePercent;
    }

    public void setLatestPrice(double latestPrice){
        this.latestPrice=latestPrice;
    }

    public String getSymbol(){
        return  symbol;
    }

    public String getCompanyName(){
        return companyName;
    }

    public String getPrimaryExchange(){
        return primaryExchange;
    }

    public double getLatestPrice(){
        return latestPrice;
    }

    public double getChange(){
        return change;
    }

    public double getChangePercent(){
        return changePercent;
    }


    @Override
    public String toString() {
        return "Stock{" +
                "symbol=" + symbol +
                ", companyName='" + companyName + '\'' +
                ", primaryExchange" + primaryExchange +
                ", latestPrice=" + latestPrice +
                ", change=" +change +
                ", changePercent=" + changePercent +
                '}';
    }
}
