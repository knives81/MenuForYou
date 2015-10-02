package com.rest.menuforyou.domain;

import java.util.Set;

public interface EntityLanguageble {
	public abstract void setDescription(String description);

	public abstract String getDescription();

	public abstract Set<? extends Language> getEntitiesLang();

	public abstract void setMenu(Menu menu);

	public abstract Long getId();

	public abstract void mapCustomFields(EnumLanguage language);

	public abstract void mapCustomFieldsSubEntities(EnumLanguage language);

}