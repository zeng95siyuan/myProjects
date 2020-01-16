package com.bennett.data.user;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	
	//@Query("SELECT DISTINCT user FROM User ")
	List<User> findByLastName(String lastName);
	
	@Query("SELECT user_name FROM User user_name")
	@Transactional(readOnly=true)
	List<User> findUserNames(); 
	
	@Query("SELECT first_name FROM User first_name")
	@Transactional(readOnly=true)
	List<User> findFirstNames(); 
	
	@Query("SELECT last_name FROM User last_name")
	@Transactional(readOnly=true)
	List<User> findLastNames(); 
	
	@Query("SELECT user FROM User user left join fetch user.transactions WHERE user.id =:id")
    @Transactional(readOnly = true)
    User findById(@Param("id") Integer id);

	
}
