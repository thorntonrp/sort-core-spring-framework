/*
 * Copyright (C) 2013 Intellectual Reserve, Inc. All rights reserved.
 * This notice may not be removed.
 */
package org.lds.example.profiles;

/**
 *
 * @author Robert Thornton <thorntonrp@ldschurch.org>
 */
public interface Profiles {

	public static final String DEFAULT		= "default";
	public static final String OFFLINE		= "offline";
	public static final String NOT_OFFLINE	= "!offline";

	public static final String DEFAULT_PROPERTIES = "META-INF/spring/default.properties";
	public static final String OFFLINE_PROPERTIES = "META-INF/spring/offline.properties";
}
