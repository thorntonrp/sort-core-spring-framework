/*
 * Copyright (C) 2013 Intellectual Reserve, Inc. All rights reserved.
 * This notice may not be removed.
 */
package org.lds.example.console.command;

import java.io.IOException;

import org.lds.media.image.model.Image;
import org.lds.media.image.model.ImageCollection;
import org.springframework.stereotype.Component;

import static java.util.Collections.sort;

/**
 *
 * @author Robert Thornton <robert.p.thornton@gmail.com>
 */
@Component
public class List extends Command {

	@Override
	public void execute(String... args) throws IOException {
		if (args.length > 1) {
			for (int i = 1; i < args.length; i++) {
				String collectionId = args[i];
				listImages(collectionId);
			}
		} else {
			String collectionId = context.get("collectionId");
			if (collectionId == null) {
				listCollections();
			} else {
				listImages(collectionId);
			}
		}
	}

	@Override
	public String getDescription() {
		return "Lists all items under the current context.";
	}

	private void listImages(String collectionId) throws IOException {
		java.util.List<Image> images = imageRepository.getImagesByCollection(collectionId);
		sort(images);
		out.println(
				"----------------------------------------------------------\n" +
				" Available Images\n" +
				"----------------------------------------------------------");
		for (Image image : images) {
			out.println(image.getId());
		}
		out.flush();
	}

	private void listCollections() throws IOException {
		java.util.List<ImageCollection> collections = imageRepository.getImageCollections();
		sort(collections);
		out.println(
				"----------------------------------------------------------\n" +
				" Available Collections\n" +
				"----------------------------------------------------------");
		for (ImageCollection collection : collections) {
			out.println(collection.getId());
		}
		out.flush();
	}
}
