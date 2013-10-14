/*
 * Copyright (C) 2013 Intellectual Reserve, Inc. All rights reserved.
 * This notice may not be removed.
 */
package org.lds.example;

import java.util.Locale;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.Lifecycle;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import static org.lds.example.Main.LINE;
import static org.lds.stack.logging.LogUtils.*;

/**
 *
 * @author Robert Thornton <thorntonrp@ldschurch.org>
 */
@Controller
@Lazy(false)
public class Application implements Lifecycle {

	private static final Logger LOG = getLogger();

	private boolean running;

	@Autowired
	private MessageSource messages;

	@Value("${application.id}")
	private String applicationId;

	@Override
	public void start() {
		info(LOG, "Starting up application controller...");

		// TODO code your application logic
		info(LOG, LINE + "\n " + message("application.title", applicationId) + LINE);

		running = true;
	}

	@Override
	public void stop() {
		info(LOG, "Shutting down application controller...");
		running = false;
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	private String message(String code, String... args) {
		return messages.getMessage(code, args, Locale.getDefault());
	}
}
