package cloud.model.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.controller.EncryptionUtility;

@Entity
public class User extends BaseModel implements Serializable {
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String surname;
	
	@Email
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private boolean active;
	
	@OneToMany(mappedBy="author", fetch=FetchType.EAGER)
//	@JsonManagedReference
	@JsonIgnore
	private List<Record> records;
	
	/* public methods */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@JsonIgnore		// it is ignored when getting an user
	public String getPassword() {
		return password;
	}
	@JsonProperty	// it is serialized when posting an user
	public void setPassword(String password) {
		this.password = password;
		//this.password = EncryptionController.sha1(password);
	}
	public boolean getActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public List<Record> getRecords() {
		return records;
	}
	public void setRecords(List<Record> records) {
		this.records = records;
	}
	
	public static class UserWrapper implements UserDetails{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		 private User user;
		 
	    public UserWrapper(User user) {
	        this.user = user;
	    }
	 
	    @Override
	    public String getUsername() {
	        return user.getEmail();
	    }
	 
	    @Override
	    public String getPassword() {
	    	String[] saltAndPass = user.getPassword().split("\\$");
	        if (saltAndPass.length != 2) {
	            throw new IllegalStateException(
	                "The stored password have the form 'salt$hash'");
	        }
	        try {
				String ret = EncryptionUtility.hash(saltAndPass[1], Base64.decodeBase64(saltAndPass[0]));
				return ret;
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
	        return null;
	    }
		 
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isAccountNonExpired() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isAccountNonLocked() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isEnabled() {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
}
