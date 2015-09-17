package com.rest.menuforyou.util;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.rest.menuforyou.domain.EnumLanguage;
import com.rest.menuforyou.domain.Typedish;

public class MenuIdentityMap {

	private static ConcurrentHashMap<KeyMenuInMemory, MenuInMemory> menuMap = new ConcurrentHashMap<KeyMenuInMemory, MenuInMemory>();
	private static ConcurrentHashMap<KeyMenuInMemory, Boolean> menuToBeUpdated = new ConcurrentHashMap<KeyMenuInMemory, Boolean>();

	private static class Holder {
		static final MenuIdentityMap INSTANCE = new MenuIdentityMap();
	}

	public static MenuIdentityMap getInstance() {
		return Holder.INSTANCE;
	}

	public void flagToBeUpdated(Long idMenu) {
		for (EnumLanguage language : EnumLanguage.values()) {
			KeyMenuInMemory key = new KeyMenuInMemory(idMenu, language);
			if (menuMap.containsKey(key)) {
				menuToBeUpdated.put(key, true);
			}
		}
	}

	public void flagUpdated(Long idMenu, EnumLanguage language) {
		KeyMenuInMemory key = new KeyMenuInMemory(idMenu, language);
		menuToBeUpdated.put(key, false);

	}

	public Boolean needToReload(Long idMenu, EnumLanguage language) {
		KeyMenuInMemory key = new KeyMenuInMemory(idMenu, language);
		if (null == menuToBeUpdated.get(key)) {
			return false;
		}
		else {
			return menuToBeUpdated.get(key);
		}
	}

	public void putMenu(Long idMenu, EnumLanguage language, List<Typedish> typedishes) {
		KeyMenuInMemory key = new KeyMenuInMemory(idMenu, language);
		MenuInMemory menu = new MenuInMemory(typedishes);
		menuMap.put(key, menu);
	}

	public List<Typedish> getMenu(Long idMenu, EnumLanguage language) {
		MenuInMemory menuInMemory = menuMap.get(new KeyMenuInMemory(idMenu, language));
		if (null != menuInMemory) {
			return menuInMemory.getTypedishes();
		}
		else {
			return null;
		}
	}
}
