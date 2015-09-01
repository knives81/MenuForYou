package com.rest.menuforyou.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_MENU")
public class Menu {

	public Menu() {
	}

	public Menu(String name) {
		this.name = name;
	}

	private Long id;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String name;

	@Column(name = "MENU_NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private Date lastTouched;

	@Column(name = "LastTouched", columnDefinition =
			"TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastTouched() {
		return lastTouched;
	}

	public void setLastTouched(Date lastTouched) {
		this.lastTouched = lastTouched;
	}

	private User user;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "USER")
	@Basic(optional = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
