package com.rest.menuforyou.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class EntityWithLanguage implements Serializable, EntityLanguageble {

	private static final long serialVersionUID = 1L;

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

	private String username;

	@Column(name = "USER")
	@JsonIgnore
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private String description;

	@Transient
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private Long order;

	@Transient
	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	private SequenceNumber sequenceNumber;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDERDISPLAY")
	@JsonIgnore
	public SequenceNumber getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(SequenceNumber order) {
		this.sequenceNumber = order;
	}

	private Menu menu;

	@ManyToOne(optional = false)
	@JoinColumn(name = "MENU_ID")
	@JsonIgnore
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
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

	public void mapCustomFields(EnumLanguage language) {
		mapLanguage(language);
		mapOrder();
	}

	private void mapLanguage(EnumLanguage language) {

		for (Language<?> entityLanguage : getEntitiesLang()) {
			if (entityLanguage.getLanguage().equals(language)) {
				setDescription(entityLanguage.getDescription());
				return;
			}
		}
		// String message = "language not found";
		// System.err.println(message);
		// throw new GenericException(Error.UNKNOWN, message);
	}

	private void mapOrder() {
		setOrder(getSequenceNumber().getNumber());
	}

	public String toString() {
		return this.getClass().getName() + "[id:" + id + ",description:" + description + ", order:" + order;
	}

}
