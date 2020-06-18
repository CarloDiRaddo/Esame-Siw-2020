package it.uniroma3.siw.esame.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class Credentials {
	
	public static final String DEFAULT_ROLE = "DEFAULT";
    public static final String ADMIN_ROLE = "ADMIN";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	
	@Column(unique = true, nullable = false)
	private String userName;
	
	@Column(nullable = false)
	private String password;
	
	@OneToOne(cascade = CascadeType.ALL)
	private User user;
	
	@Column(nullable = false)
	private String role;
	
	 @Column(updatable = false, nullable = false)
	    private LocalDateTime creationTimestamp;

	 @Column(nullable = false)
	  	private LocalDateTime lastUpdateTimestamp;
	 
	 @PrePersist
	 public void creazioneTimestamp() {
		 this.creationTimestamp = LocalDateTime.now();
		 this.lastUpdateTimestamp = LocalDateTime.now();
	 }
	 
	 @PreUpdate
	    protected void onUpdate() {
	        this.lastUpdateTimestamp = LocalDateTime.now();
	    }
	
	public Credentials() {}
	
	public Credentials(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}		
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

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

	public User getUser() {
		return user;
	}

	public void setUser(User utente) {
		this.user = utente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credentials other = (Credentials) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}	
}
