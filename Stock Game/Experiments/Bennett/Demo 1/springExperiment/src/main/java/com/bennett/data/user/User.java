package com.bennett.data.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import com.bennett.data.transaction.Transaction;
import com.bennett.model.Person;

@Entity
@Table(name = "users")
public class User extends Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "PASSWORD")
	private String password;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
	private Set<Transaction> transactions;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	protected Set<Transaction> getTransactionsInternal() {
		if (this.transactions == null) {
			this.transactions = new HashSet<>();
		}
		return this.transactions;
	}

	protected void setTransactionsInternal(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	public List<Transaction> getTransactions() {
		List<Transaction> sortedTransactions = new ArrayList<>(getTransactionsInternal());
		PropertyComparator.sort(sortedTransactions, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedTransactions);
	}

	public void addTransaction(Transaction transaction) {
		if (transaction.isNew()) {
			getTransactionsInternal().add(transaction);
		}
		transaction.setUserId(this.getId());
	}

	/**
	 * Return the Transaction with the given name, or null if none found for this
	 * Owner.
	 *
	 * @param name
	 *            to test
	 * @return true if pet name is already in use
	 */

	public String toString() {
		return "Name: " + this.getFirstName() + " " + this.getLastName() + " Username: " + this.userName + " Password: "
				+ this.password;
	}

}
