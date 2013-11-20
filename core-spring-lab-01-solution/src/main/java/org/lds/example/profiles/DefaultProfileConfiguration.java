/*
 * Copyright (C) 2013 Intellectual Reserve, Inc. All rights reserved.
 * This notice may not be removed.
 */
package org.lds.example.profiles;

import java.util.logging.Logger;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import static org.lds.example.profiles.Profiles.*;
import static org.lds.stack.logging.LogUtils.*;

/**
 *
 * @author Robert Thornton <thorntonrp@ldschurch.org>
 */
@Profile(NOT_OFFLINE)
@PropertySource({DEFAULT_PROPERTIES})
@Configuration("profileConfiguration")
class DefaultProfileConfiguration extends AbstractProfileConfiguration {

	private static final Logger LOG = getLogger();

	static {
		info(LOG, "Loading \"{0}\" profile...", DEFAULT);
	}

	DefaultProfileConfiguration() {
		super(DEFAULT);
	}
}
