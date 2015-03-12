package com.epam.idea.rest.controller.idea;

/**
 * This class contains the constants that are used in our integration tests and DbUnit datasets for IdeaController.
 */
final class IdeaConstants {

	static final long IDEA_ID = 1L;
	static final int RATING = 5;
	static final String TITLE = "title";
	static final String DESCRIPTION = "description";


	static final long USER_ID = 1L;
	static final String USERNAME = "Jack";
	static final String EMAIL = "Jack@test.com";
	static final String PASSWORD = "password";

	static final long TAG_ID = 1L;
	static final String NAME = "Programming";


	private IdeaConstants() {
		//empty
	}
}
