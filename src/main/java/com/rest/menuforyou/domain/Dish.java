package com.rest.menuforyou.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "TB_DISH")
public class Dish extends EntityWithLanguage implements Serializable {

	private static final long serialVersionUID = 8326573469891220946L;
	private String name;

	@Column(name = "DISH_NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private float price;

	@Column(name = "DISH_PRICE")
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	private String imageUrl;

	@Column(name = "DISH_IMAGE_URL")
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	private Set<DishLanguage> entitiesLang = new HashSet<DishLanguage>();

	@OneToMany(mappedBy = "entity", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JsonIgnore
	public Set<DishLanguage> getEntitiesLang() {
		return entitiesLang;
	}

	public void setEntitiesLang(Set<DishLanguage> entitiesLang) {
		this.entitiesLang = entitiesLang;
	}

	private Typedish typedish;

	@JsonView(Views.ViewFromDish.class)
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "DISH_TYPEDISH_ID")
	public Typedish getTypedish() {
		return typedish;
	}

	public void setTypedish(Typedish typedish) {
		this.typedish = typedish;
	}

	private Set<Ingredient> ingredients;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "TB_DISH_INGREDIENT", joinColumns = { @JoinColumn(name = "DISH_INGREDIENT_ID1") }, inverseJoinColumns = { @JoinColumn(name = "DISH_INGREDIENT_ID2") })
	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public String toString() {
		return super.toString() + ",name:" + name + ",price:" + price
				+ ",imageUrl:" + imageUrl + "]";
	}

}