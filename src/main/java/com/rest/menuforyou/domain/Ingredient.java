package com.rest.menuforyou.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_INGREDIENT")
public class Ingredient extends EntityWithLanguage implements Serializable {

	private static final long serialVersionUID = 8326573469891220946L;

	private Set<IngredientLanguage> entitiesLang = new HashSet<IngredientLanguage>();

	@OneToMany(mappedBy = "entity", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JsonIgnore
	public Set<IngredientLanguage> getEntitiesLang() {
		return entitiesLang;
	}

	public void setEntitiesLang(Set<IngredientLanguage> entitiesLang) {
		this.entitiesLang = entitiesLang;
	}

	private Set<Dish> dishes = new HashSet<Dish>();

	@ManyToMany
	@JoinTable(name = "TB_DISH_INGREDIENT", joinColumns = { @JoinColumn(name = "DISH_INGREDIENT_ID2") }, inverseJoinColumns = { @JoinColumn(name = "DISH_INGREDIENT_ID1") })
	@JsonIgnore
	public Set<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(Set<Dish> dishes) {
		this.dishes = dishes;
	}

	public void mapCustomFieldsSubEntities(EnumLanguage language) {

	}
}
