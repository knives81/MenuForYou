package come.rest.menuforyou.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.rest.menuforyou.domain.EnumLanguage;
import com.rest.menuforyou.domain.Typedish;

public class MenuIdentityMap {

	private static HashMap<KeyMenuInMemory, MenuInMemory> menuMap = new HashMap<KeyMenuInMemory, MenuInMemory>();
	private static HashSet<Long> menuToBeUpdated = new HashSet<Long>();

	private static class Holder {
		static final MenuIdentityMap INSTANCE = new MenuIdentityMap();
	}

	public static MenuIdentityMap getInstance() {
		return Holder.INSTANCE;
	}

	public synchronized void flagToBeUpdated(Long idMenu) {
		menuToBeUpdated.add(idMenu);
	}

	public synchronized boolean needToReload(Long idMenu) {
		if (menuToBeUpdated.contains(idMenu)) {
			menuToBeUpdated.remove(idMenu);
			return true;
		}
		return false;
	}

	public void putMenu(Long idMenu, EnumLanguage language, List<Typedish> typedishes) {
		KeyMenuInMemory key = new KeyMenuInMemory(idMenu, language);
		MenuInMemory menu = new MenuInMemory(typedishes);
		menuMap.put(key, menu);
	}

	public List<Typedish> getMenu(Long idMenu, EnumLanguage language) {
		return (menuMap.get(new KeyMenuInMemory(idMenu, language))).getTypedishes();
	}
}
