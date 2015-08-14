package com.rest.menuforyou.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "TB_RESTAURANT")
public class Restaurant implements Serializable {

	private static final long serialVersionUID = 8326573469891220946L;

	private Long id;

	@Id
	@Column(name = "RESTAURANT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String name;

	@Column(name = "RESTAURANT_NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String address;

	@Column(name = "RESTAURANT_ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	private String imageUrl;

	@Column(name = "RESTAURANT_IMAGE_URL")
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	private User user;

	@JsonView(Views.ViewWithUser.class)
	@OneToOne
	@JoinColumn(name = "RESTAURANT_USER")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private Menu menu;

	@OneToOne
	@JoinColumn(name = "RESTAURANT_MENU_ID")
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}