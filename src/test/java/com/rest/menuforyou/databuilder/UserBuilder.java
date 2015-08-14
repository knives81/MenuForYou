package com.rest.menuforyou.databuilder;

import com.rest.menuforyou.domain.User;

public class UserBuilder {
	User user = new User();

	public static UserBuilder user()
	{
		return new UserBuilder();
	}

	public User build() {
		return user;
	}

	public UserBuilder withUsername(String username) {
		user.setUsername(username);
		return this;
	}

	public UserBuilder withPassword(String password) {
		user.setPassword(password);
		return this;
	}

	public UserBuilder isEnabled() {
		user.setEnabled(true);
		return this;
	}

	public UserBuilder isAdmin() {
		user.setAuthority("ADMIN");
		return this;
	}

	public UserBuilder notEnabled() {
		user.setEnabled(false);
		return this;
	}

}
