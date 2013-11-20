/*
 * Copyright (C) 2013 Intellectual Reserve, Inc. All rights reserved.
 * This notice may not be removed.
 */
package org.lds.example.profiles;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import static org.lds.stack.logging.LogUtils.*;

/**
 * Base profile configuration class. Profile configuration classes extend this
 * class simply for the purpose of printing the name(s) of the active profile(s)
 * to the log.
 *
 * @author Robert Thornton <thorntonrp@ldschurch.org>
 */
public class AbstractProfileConfiguration {

	private static final Logger LOG = getLogger();

	private final String profileName;

	protected AbstractProfileConfiguration(String profileName) {
		this.profileName = profileName;
	}

	/**
	 * Prints a message to the log that identifies the active profile name.
	 */
	@PostConstruct
	void init() {
		info(LOG, "Profile configuration initialized: " +
				  "\"{0}\" -> \"{1}\"", profileName);
	}
}
