package com.rest.menuforyou.domain;

public class Views {
	public interface ViewWithIngredient {
	}

	public interface ViewFromDish extends ViewWithIngredient {
	}

	public interface ViewFromTypedish extends ViewWithIngredient {
	}

	public static class ViewWithFeedback {
	}
}
