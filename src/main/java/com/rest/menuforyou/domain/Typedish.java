package com.rest.menuforyou.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "TB_TYPEDISH")
public class Typedish extends EntityWithLanguage implements Serializable {

	private static final long serialVersionUID = 8326573469891220946L;

	private Set<TypedishLanguage> entitiesLang = new HashSet<TypedishLanguage>();

	@OneToMany(mappedBy = "entity", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JsonIgnore
	public Set<TypedishLanguage> getEntitiesLang() {
		return entitiesLang;
	}

	public void setEntitiesLang(Set<TypedishLanguage> entitiesLang) {
		this.entitiesLang = entitiesLang;
	}

	private Set<Dish> dishes;

	@JsonView(Views.ViewFromTypedish.class)
	@OneToMany(mappedBy = "typedish", fetch = FetchType.EAGER)
	@OrderBy("sequenceNumber ASC")
	public Set<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(Set<Dish> dishes) {
		this.dishes = dishes;
	}
}
