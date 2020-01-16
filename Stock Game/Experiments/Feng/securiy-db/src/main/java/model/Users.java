package model;
import java.util.Set;

import javax.management.relation.Role;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "user")
public class Users {

	@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "user_id")
		private int id;
		@Column(name = "user_name")
		private String name;
		@Column(name = "pass_word")
		protected String Password;
		@Column(name = "first_name")
		private String FirstName;
		@Column(name = "last_name")
		private String LastName;
		@Column(name = "permission")
		private boolean pmn;
//		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER);
//		@JoinTable(name = "user_role", joinColumns = @JoinColumns(name = "user_role"))
	private Set<Role> roles;
	
	
	public Users(Users users) {
		this.id = users.getId();
		this.name = users.getUsername();
		this.FirstName = users.getFirstName();
		this.LastName = users.getLastName();
		this.pmn = users.getPmn();
		this.Password = users.getPassword();
	}
	
	private boolean getPmn() {
		
		return pmn;
	}

	private String getLastName() {
		
		return LastName;
	}

	private String getFirstName() {
		
		return FirstName;
	}

	private int getId() {
		
		return id;
	}

	public String getPassword() {
		
		return Password;
	}

	public String getUsername() {
		
		return name;
	}
	}
