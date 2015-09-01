package com.rest.menuforyou.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_FEEDBACK")
public class Feedback implements Serializable {

	private static final long serialVersionUID = 8326573469891220946L;

	private Long id;

	@Id
	@Column(name = "FEEDBACK_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Long rating;

	@Column(name = "FEEDBACK_RATING")
	@Basic(optional = false)
	public Long getRating() {
		return rating;
	}

	public void setRating(Long rating) {
		this.rating = rating;
	}

	private String message;

	@Column(name = "FEEDBACK_MESSAGE")
	@Basic(optional = false)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private Dish dish;

	@JsonIgnore
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "FEEDBACK_DISH_ID")
	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
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

}