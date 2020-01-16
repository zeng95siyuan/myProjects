package com.server.user;

import org.springframework.data.repository.CrudRepository;
/**
 * Interface for accessing the bank table
 * @author Bennett Ray
 *
 */
public interface BankRepository extends CrudRepository<Bank, Integer> {

}
