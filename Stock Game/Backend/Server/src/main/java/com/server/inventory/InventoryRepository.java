package com.server.inventory;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * Interface for accessing inventory table
 * @author Bennett Ray
 *
 */
public interface InventoryRepository extends CrudRepository<Inventory, Integer>{

	/**
	 * Get list of inventory entries for a user
	 * @param userId The id of the user 
	 * @return List of inventory objects
	 */
	List<Inventory> findByUserId(int userId);
	
	/**
	 * Get inventory by user id and stock id
	 * @param userId The id of the user
	 * @param stockId The id of the stock associated with the inventory entry
	 * @return Inventory object
	 */
	Inventory findByUserIdAndStockId(int userId, int stockId);
	
	/**
	 * Get quantity of the inventory entry by user id and stock id
	 * @param userId The id of the user
	 * @param stockId The id of the stock associated with the inventory entry
	 * @return Inventory quantity
	 */
	int findQuantityByUserIdAndStockId(int userId, int stockId);
	
	
	
}
