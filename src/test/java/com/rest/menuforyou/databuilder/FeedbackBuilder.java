package com.rest.menuforyou.databuilder;

import com.rest.menuforyou.domain.Feedback;

public class FeedbackBuilder {
	Feedback feedback = new Feedback();

	public static FeedbackBuilder feedback() {
		return new FeedbackBuilder();
	}

	public Feedback build() {
		return feedback;
	}

	public FeedbackBuilder withRating(Long rating) {
		feedback.setRating(rating);
		return this;
	}

	public FeedbackBuilder withMessage(String message) {
		feedback.setMessage(message);
		;
		return this;
	}

}
