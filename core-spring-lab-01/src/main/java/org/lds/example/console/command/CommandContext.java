/*
 * Copyright (C) 2013 Intellectual Reserve, Inc. All rights reserved.
 * This notice may not be removed.
 */

package org.lds.example.console.command;

import java.util.Map;

import org.springframework.stereotype.Component;

import static org.lds.stack.utils.CollectionUtils.newMap;

/**
 *
 * @author Robert Thornton <robert.p.thornton@gmail.com>
 */
@Component
public class CommandContext {

	private final Map<String, String> context = newMap();

	public String get(String key) {
		return context.get(key);
	}

	public void set(String key, String value) {
		context.put(key, value);
	}

	public void remove(String key) {
		context.remove(key);
	}
}
