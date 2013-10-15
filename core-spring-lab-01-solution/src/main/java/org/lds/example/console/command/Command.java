/*
 * Copyright (C) 2013 Intellectual Reserve, Inc. All rights reserved.
 * This notice may not be removed.
 */

package org.lds.example.console.command;


import java.io.IOException;
import java.io.PrintWriter;

import org.lds.media.image.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

/**
 *
 * @author Robert Thornton <robert.p.thornton@gmail.com>
 */
public abstract class Command {

	@Autowired
	protected MessageSource messages;

	@Autowired
	protected CommandContext context;

	@Autowired
	protected PrintWriter out;

	@Autowired
	protected ImageRepository imageRepository;

	public abstract void execute(String... args) throws IOException;

	public abstract String getDescription();
}
