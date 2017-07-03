package org.alan.ml.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_TABLE")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1560747487828694738L;

	@Id
	@Column(name = "userName", nullable = false)
	private String userName;

	@Column(name = "userRole", nullable = false)
	private String userRole;

	@Column(name = "pwSalt", nullable = false)
	private String pwSalt;

	@Column(name = "pwPhase", nullable = false)
	private String pwPhase;

	@Column(name = "active", nullable = false)
	private boolean active;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getPwSalt() {
		return pwSalt;
	}

	public void setPwSalt(String pwSalt) {
		this.pwSalt = pwSalt;
	}

	public String getPwPhase() {
		return pwPhase;
	}

	public void setPwPhase(String pwPhase) {
		this.pwPhase = pwPhase;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", userRole=" + userRole + ", active=" + active + "]";
	}

}
