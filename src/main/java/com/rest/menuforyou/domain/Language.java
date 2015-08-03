package com.rest.menuforyou.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Language<T extends EntityWithLanguage> implements Serializable {

	private static final long serialVersionUID = 8326573469891220946L;
	private Long id;
	private EnumLanguage language;
	private String description;

	public Language() {
	}

	public Language(EnumLanguage language, String description, T entity) {
		super();
		this.language = language;
		this.description = description;
		this.entity = entity;
	}

	private T entity;

	@ManyToOne(optional = false)
	@JoinColumn(name = "LANG_IDENTITY")
	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	@Id
	@Column(name = "LANG_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "LANG_LANGUAGE")
	@Enumerated(EnumType.STRING)
	public EnumLanguage getLanguage() {
		return language;
	}

	public void setLanguage(EnumLanguage language) {
		this.language = language;
	}

	@Column(name = "LANG_DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		return "id:" + id + " language:" + language + " description:" + description + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((language == null) ? 0 : language.hashCode());
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
		Language other = (Language) obj;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		return true;
	}

}
