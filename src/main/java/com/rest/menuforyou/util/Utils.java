package com.rest.menuforyou.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.rest.menuforyou.domain.Menu;
import com.rest.menuforyou.domain.Restaurant;
import com.rest.menuforyou.error.NoPermissionException;

public class Utils {

	public static String getUsernameLogged() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}

	public static void checkPermission(String usernameOwner, String usernameLogged) {
		if (!usernameOwner.equals(usernameLogged)) {
			throw new NoPermissionException("User " + usernameLogged + " not authorized");
		}
	}
	
	public static void checkPermission(Menu menu) {
		String usernameLogged = Utils.getUsernameLogged();
		checkPermission(menu.getUser().getUsername(), usernameLogged);
	}

	public static void checkPermission(Restaurant restaurant) {
		String usernameLogged = Utils.getUsernameLogged();
		checkPermission(restaurant.getUser().getUsername(), usernameLogged);
	}

}
