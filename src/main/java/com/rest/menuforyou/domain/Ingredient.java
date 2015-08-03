package com.rest.menuforyou.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TB_INGREDIENT")
public class Ingredient extends EntityWithLanguage {

	private static final long serialVersionUID = 1L;

}
