package cloud.model.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import cloud.controller.EncryptionController;

@Entity
public class User {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String userId;

	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String surname;
	
	@Email
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date registrationDateTime;
		
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateDateTime;
	
	@OneToMany(mappedBy="author", fetch=FetchType.EAGER)
	private List<Record> records;
	
	/* public methods */
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String id) {
		this.userId = id;
	}
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = EncryptionController.sha1(password);
	}
	public Date getRegistrationDateTime() {
		return registrationDateTime;
	}
	public void setRegistrationDateTime(Date registrationDateTime) {
		this.registrationDateTime = registrationDateTime;
	}
	public Date getLastUpdateDateTime() {
		return lastUpdateDateTime;
	}
	public void setLastUpdateDateTime(Date lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}
	
	public List<Record> getRecords() {
		return records;
	}
	public void setRecords(List<Record> records) {
		this.records = records;
	}
}
