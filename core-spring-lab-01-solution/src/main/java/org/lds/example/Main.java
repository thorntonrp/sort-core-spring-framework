/*
 * Copyright (C) 2013 Intellectual Reserve, Inc. All rights reserved.
 * This notice may not be removed.
 */
package org.lds.example;

import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import static org.lds.stack.logging.LogUtils.*;
import static org.lds.stack.utils.StringUtils.padRight;

/**
 * Main entry point. Starts up the Spring application context and invokes the
 * main controller.
 *
 * @author Robert Thornton <thorntonrp@ldschurch.org>
 */
public class Main {

	private static final Logger LOG = getLogger();

	public static final String LINE = padRight("\n", '-', 73);

	/**
	 * Application entry point. Starts up the Spring application context and
	 * invokes the main controller.
	 *
	 * @param args command-line arguments (ignored)
	 */
	public static void main(String[] args) {
		GenericApplicationContext context;

		// Initialize the application context from the annotatd configuration class
		info(LOG, "Initializing Spring application context");
		context = new AnnotationConfigApplicationContext(MainConfiguration.class);

		// Propogate the start signal to all relevant beans
		info(LOG, "Starting all lifecycle beans.");
		context.start();

		// List all beans in the Spring application context
		listBeans(context);

		// Propogate the stop signal to all relevant beans
		info(LOG, "Stopping all lifecycle beans.");
		context.stop();

		// If not closed explicitly, it will be closed by a JVM shutdown hook
		info(LOG, "Destroying all beans.");
		context.close();
	}

	//-- Private Implementation ----------------------------------------------//

	/**
	 * Lists all beans in the Spring application context.
	 *
	 * @param context the Spring application context
	 */
	private static void listBeans(ApplicationContext context) {
		StringBuilder out = new StringBuilder();
		out.append(LINE);
		out.append("\n Bean Definitions:");
		for (String name : context.getBeanDefinitionNames()) {
			out.append("\n\t").append(name);
		}
		out.append(LINE);
		info(LOG, out.toString());
	}

	private Main() {}
}