package com.server.inventory;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.stock.Stock;
import com.server.stock.StockService;
import com.server.transaction.TransactionService;
import com.server.user.UserService;

/**
 * Methods for inventory functions
 *
 * @author Bennett Ray
 */
@Service
public class InventoryService {

    /**
     * Interface for connecting to inventory table
     */
    @Autowired
    InventoryRepository userStocks;

    /**
     * Service for Stocks
     */
    @Autowired
    StockService stockService;

    /**
     * Service for User transactions
     */
    @Autowired
    TransactionService transactionService;

    /**
     * Service for Users
     */
    @Autowired
    UserService userService;

    /**
     * Sell a certain number of stocks. If the user will have no shares of the
     * stocks after the purchase, the inventory object will be deleted. Also saves
     * the transaction to the transaction table.
     *
     * @param userId  Id of the user who is selling stocks
     * @param stockId Id of the stock being sold
     * @param num     Number of stocks to be sold
     * @return If the user does not own any of the stocks being sold returns "User
     * does not own any shares of {stock symbol}". If num is greater than
     * the number of stocks the user owns than num is set to the number of
     * stocks owned. Otherwise returns "{num} share(s) of {stock symbol}
     * sold".
     */
    public String sellStock(int userId, int stockId, int num) {
        Inventory inventory = userStocks.findByUserIdAndStockId(userId, stockId);
        Stock stock = stockService.findById(stockId);
        if (inventory == null) {
            return "User does not own any shares of " + stock.getSymbol();
        }
        // prevents users from selling more stocks than they actually own
        if (num > inventory.getQuantity()) {
            num = inventory.getQuantity();
        }
        inventory.setQuantity(inventory.getQuantity() - num);
        userService.sellStock(userId, stock.getPrice(), num);
        if (inventory.getQuantity() == 0) {
            // removes stock from inventory if the user has sold their shares
            userStocks.delete(inventory);
        } else {
            // update inventory table
            userStocks.save(inventory);
        }

        transactionService.save(userId, stock, false, num);

        return "" + num + " share(s) of " + stock.getSymbol() + " sold";
    }

    /**
     * Buy a certain number of stocks. If the user does not own any shares of the
     * stock, a new inventory object is created. Also saves the transaction to the
     * transaction table.
     *
     * @param userId  Id of the user who is buying stocks
     * @param stockId Id of the stock being bought
     * @param num     Number of stocks to be bought
     * @return If the user cannot afford the stock returns "Not Enough Money"
     */
    public String buyStock(int userId, int stockId, int num) {
        Inventory inventory = userStocks.findByUserIdAndStockId(userId, stockId);
        if (inventory == null) {
            inventory = new Inventory(userId, stockService.findById(stockId));
        }
        int numPurchased = userService.buyStock(userId, inventory.getStock().getPrice(), num);
        if (numPurchased == 0) {
            return "Not enough money";
        } else {
            inventory.setQuantity(inventory.getQuantity() + numPurchased);
            userStocks.save(inventory);
        }
        transactionService.save(userId, stockService.findById(stockId), true, num);

        return "" + numPurchased + " share(s) of " + inventory.getStock().getSymbol() + " purchased";

    }

    /**
     * Gets the user's stocks
     *
     * @param userId Id of the user whose portfolio is being requested
     * @return A list of stocks of each inventory object
     */
    public List<Stock> getPortfolio(int userId) {
        List<Inventory> inventoryList = userStocks.findByUserId(userId);
        List<Stock> stockList = new ArrayList<>();
        for (Inventory i : inventoryList) {
            stockList.add(i.getStock());
        }
        return stockList;
    }

    /**
     * Gets the user's portfolio value
     *
     * @param userId Id of the user who portfolio value is being requested
     * @return The sum of the price of the user's stocks.
     */
    public Double getPortfolioValue(int userId) {
        List<Stock> stockList = this.getPortfolio(userId);
        Double total = 0.0;
        for (Stock stock : stockList) {
            total += (stock.getPrice() * stock.getQuantity());
        }
        return Math.round(total * 100.0) / 100.0;
    }


    public List<Stock> drawStock(int userId, int[] stockIdArr, int[] numArr) {
        List<Stock> myList = new ArrayList<>();

        for (int i = 0; i < stockIdArr.length; i++) {
            Inventory inventory = userStocks.findByUserIdAndStockId(userId, stockIdArr[i]);
            if (inventory == null) {
                inventory = new Inventory(userId, stockService.findById(stockIdArr[i]));
            }
            int numDrawed = userService.drawStock(userId, numArr[i]);
            if (numDrawed == 0) {
                return null;
            } else {
                inventory.setQuantity(inventory.getQuantity() + numArr[i]);
                userStocks.save(inventory);
            }
            Stock object = new Stock(inventory.getStock().getSymbol(), inventory.getStock().getPrice(), numArr[i]);
            myList.add(object);
        }
        return myList;
    }


}
