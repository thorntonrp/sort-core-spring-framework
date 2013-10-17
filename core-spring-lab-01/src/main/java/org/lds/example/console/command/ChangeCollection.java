/*
 * Copyright (C) 2013 Intellectual Reserve, Inc. All rights reserved.
 * This notice may not be removed.
 */
package org.lds.example.console.command;

import java.io.IOException;

import org.lds.media.image.model.ImageCollection;
import org.springframework.stereotype.Component;

/**
 *
 * @author Robert Thornton <robert.p.thornton@gmail.com>
 */
@Component("cd")
public class ChangeCollection extends Command {

	@Override
	public void execute(String... args) throws IOException {
		if (args.length < 2) {
			out.println("Expected a collectionId or '..'");
		}
		String collectionId = args[1];
		if ("..".equals(collectionId)) {
			context.remove("collectionId");
		} else {
			ImageCollection collection = imageRepository.getImageCollection(collectionId);
			if (collection == null) {
				out.println("Invalid collectionId");
			}
			context.set("collectionId", args[1]);
		}
	}

	@Override
	public String getDescription() {
		return "Changes context to a different collection.";
	}
}
