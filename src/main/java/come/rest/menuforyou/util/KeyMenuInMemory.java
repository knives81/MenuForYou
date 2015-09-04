package come.rest.menuforyou.util;

import com.rest.menuforyou.domain.EnumLanguage;

public class KeyMenuInMemory {
	private final Long idMenu;
	private final EnumLanguage language;

	public KeyMenuInMemory(Long idMenu, EnumLanguage language) {
		this.idMenu = idMenu;
		this.language = language;
	}

	public Long getIdMenu() {
		return idMenu;
	}
	public EnumLanguage getLanguage() {
		return language;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idMenu == null) ? 0 : idMenu.hashCode());
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
		KeyMenuInMemory other = (KeyMenuInMemory) obj;
		if (idMenu == null) {
			if (other.idMenu != null)
				return false;
		} else if (!idMenu.equals(other.idMenu))
			return false;
		if (language != other.language)
			return false;
		return true;
	}

}
