package com.rest.menuforyou.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_USER")
public class User implements Serializable {

	private static final long serialVersionUID = 8326573469891220946L;

	private String username;

	@Id
	@Column(name = "USERNAME", unique = true)
	@Basic(optional = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private String password;

	@Column(name = "PASSWORD")
	@Basic(optional = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String authority;

	@Column(name = "AUTHORITY")
	@Basic(optional = false)
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	private Boolean enabled;

	@Column(name = "ENABLED")
	@Basic(optional = false)
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	private Date lastTouched;

	@Column(name = "LastTouched", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastTouched() {
		return lastTouched;
	}

	public void setLastTouched(Date lastTouched) {
		this.lastTouched = lastTouched;
	}

}