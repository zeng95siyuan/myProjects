package model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails extends Users implements UserDetails {
	public CustomUserDetails(final Users users) {
		super(users);
	}


	public Collection<? extends GrantedAuthority> getAuthorities(){
	return null;
	}
	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public String getUsername() {
		return super.getUsername();
	}
	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	
	@Override
	public
	boolean isCredentialsNonExpired() {
		return true;
	}

	
	@Override
	public
	boolean isEnabled() {
		return true;
	}

}
