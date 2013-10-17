/*
 * Copyright (C) 2013 Intellectual Reserve, Inc. All rights reserved.
 * This notice may not be removed.
 */
package org.lds.example;

import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import org.lds.example.console.SimpleConsole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.Lifecycle;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import static java.util.Arrays.asList;
import static org.lds.stack.utils.CollectionUtils.isEmpty;
import static org.lds.stack.utils.StringUtils.*;
import static org.lds.stack.logging.LogUtils.*;

/**
 *
 * @author Robert Thornton <thorntonrp@ldschurch.org>
 */
@Controller
@Lazy(false)
public class Application implements Lifecycle {

	private static final Logger LOG = getLogger();

	public static final String LINE = padRight("\n", '-', 73);

	private boolean running;

	//-- Injected Dependencies -----------------------------------------------//

	@Autowired
	private MessageSource messages;

	@Autowired
	private SimpleConsole console;

	@Autowired
	private Environment env;

	@Value("${application.id}")
	private String applicationId;

	@Value("${build.date}")
	private String buildDate;

	//-- Lifecycle Implementation --------------------------------------------//

	@Override
	public void start() {
		List<String> activeProfiles = asList(env.getActiveProfiles());
		if (isEmpty(activeProfiles)) {
			activeProfiles = asList("default");
		}
		info(LOG, "Starting up the application controller...");
		info(LOG,
			LINE +
			"\n " + message("application.title", applicationId) +
			"\n " + message("build.date", buildDate) +
			"\n " + message("active.profile", join(activeProfiles, ", ")) +
			LINE);

		console.run();

		running = true;
	}

	@Override
	public void stop() {
		info(LOG, "Shutting down the application controller...");
		running = false;
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	//-- Private Implementation ----------------------------------------------//

	private String message(String code, String... args) {
		return messages.getMessage(code, args, Locale.getDefault());
	}
}
