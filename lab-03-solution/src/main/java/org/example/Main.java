/*
 * Copyright (C) 2013 Intellectual Reserve, Inc. All rights reserved.
 * This notice may not be removed.
 */
package org.example;

import static org.example.util.SpringSupport.*;

/**
 * Application entry point. Starts up the Spring application context and
 * invokes the main controller.
 *
 * @author Robert Thornton <thorntonrp@ldschurch.org>
 */
public class Main {

	/**
	 * Application entry point. Starts up the Spring application context and
	 * invokes the main controller.
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		// (Optional)
		// Prompt the user to choose an active profile if it has been supplied
		chooseActiveSpringProfile();

		// Initialize the application context from the configuration class(es)
		initSpring(MainConfiguration.class);
	}

	private Main() {}
}