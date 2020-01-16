package com.bennett.data.transaction;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer>{

	@Query("SELECT transactions FROM Transaction transactions WHERE userId=:id")
	@Transactional(readOnly = true)
	List<Transaction> findTransactionsById(@Param("id") Integer id); 
	
}
