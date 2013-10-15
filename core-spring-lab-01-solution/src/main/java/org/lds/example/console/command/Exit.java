/*
 * Copyright (C) 2013 Intellectual Reserve, Inc. All rights reserved.
 * This notice may not be removed.
 */

package org.lds.example.console.command;

import org.springframework.stereotype.Component;

/**
 *
 * @author Robert Thornton <robert.p.thornton@gmail.com>
 */
@Component
public class Exit extends Command {

	@Override
	public void execute(String... args) {
		out.println("Good-bye");
		out.flush();
	}

	@Override
	public String getDescription() {
		return "Prints a good-bye message and exits.";
	}
}
