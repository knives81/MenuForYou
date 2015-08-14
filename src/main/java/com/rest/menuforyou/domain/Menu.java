package com.rest.menuforyou.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

}
