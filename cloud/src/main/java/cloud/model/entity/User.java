package cloud.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User extends BaseModel implements Serializable, Comparable<User> {
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

	@Transient
	private BigDecimal score;
	
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
	public BigDecimal getScore() {
		BigDecimal score = new BigDecimal("0.00");
		if(this.getRecords()!= null) {
			return new BigDecimal(this.getRecords().size()+".00");
		} 
		return score;
	}
	
	@Override
    public int compareTo(User that) {
		BigDecimal xScore = this.getScore();
		BigDecimal yScore = that.getScore();
		return xScore.compareTo(yScore);
    }
}
