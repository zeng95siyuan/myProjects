package com.server.stock;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Interface for accessing stock table
 * 
 * @author Bennett Ray
 *
 */
public interface StockRepository extends CrudRepository<Stock, Integer> {

	/**
	 * Get the stock from the table by id
	 * 
	 * @param id The id of the stock
	 * @return Stock Object
	 */
	Stock findById(int id);

	/**
	 * Get the stock(s) containing the search index
	 * 
	 * @param index The search term
	 * @return List of Stock Objects
	 */
	@Query("SELECT stock FROM Stock stock WHERE stock.symbol LIKE %:index% OR stock.company LIKE %:index%")
	Stock[] findBySymbolAndCompanyName(@Param("index") String index);

}
