/*
 * Copyright (C) 2013 Intellectual Reserve, Inc. All rights reserved.
 * This notice may not be removed.
 */
package org.lds.example.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Logger;

import org.lds.example.console.command.Command;
import org.lds.example.console.command.CommandContext;
import org.lds.media.image.repository.OfflineRepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static org.lds.stack.logging.LogUtils.*;
import static org.lds.stack.utils.CollectionUtils.newMap;

/**
 * A simple controller that prompts for user commands against the LDS Media
 * Library Image Service.
 *
 * @author Robert Thornton <robert.p.thornton@gmail.com>
 */
@Controller
public class SimpleConsole {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = getLogger();

	private final BufferedReader in;
	private final PrintWriter out;

	@Autowired
	private Map<String, Command> commands;

	@Autowired
	private Command list;

	@Autowired
	private CommandContext context;

	@Autowired
	public SimpleConsole(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
	}

	public void setCommands(Map<String, Command> commands) {
		this.commands = newMap(commands);
	}

	public void run() {
		String input = null;
		do {
			try {
				input = promptForInput();
				String[] args = input.split("\\s");
				if (args[0].equals("help")) {
					printHelp();
				} else {
					Command command = commands.get(args[0]);
					if (command == null) {
						out.println("Invalid command. Try 'help' for a list of available commands.");
						out.flush();
					} else {
						command.execute(args);
					}
				}
			} catch (IllegalArgumentException | OfflineRepositoryException ex) {
				out.println(ex.getMessage());
				out.flush();
			} catch (IOException ex) {
				warning(LOG, ex.toString(), ex);
				out.println(ex.getMessage());
				out.flush();
			}
		} while (!"exit".equals(input));
	}

	private String promptForInput() throws IOException {
		String collectionId = context.get("collectionId");
		out.printf("[%s] $ ", collectionId == null ? "gallery" : "gallery/" + collectionId);
		out.flush();
		return in.readLine();
	}

	private void printHelp() {
		for (Map.Entry<String, Command> entry : commands.entrySet()) {
			out.printf("%1$-16s %2$s%n", entry.getKey(), entry.getValue().getDescription());
		}
	}
}
