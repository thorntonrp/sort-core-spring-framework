/*
 * Copyright (C) 2013 Intellectual Reserve, Inc. All rights reserved.
 * This notice may not be removed.
 */
package org.lds.example.console.command;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.lds.media.image.model.Image;
import org.lds.media.image.model.ImageType;
import org.springframework.stereotype.Component;

import static java.util.Collections.sort;
import static org.lds.stack.logging.LogUtils.*;

/**
 *
 * @author Robert Thornton <thorntonrp@ldschurch.org>
 */
@Component
public class Download extends Command {

	private static final Logger LOG = getLogger();

	@Override
	public void execute(String... args) throws IOException {
		String collectionId = context.get("collectionId");
		if (collectionId == null) {
			if (args.length == 1) {
				throw new IllegalArgumentException("No collection selected.");
			} else {
				for (int i = 1; i < args.length; i++) {
					downloadAll(args[i]);
				}
			}
		} else {
			if (args.length == 1) {
				downloadAll(collectionId);
			} else {
				for (int i = 1; i < args.length; i++) {
					Image image = imageRepository.getImage(collectionId, args[i]);
					download(collectionId, image);
				}
			}
		}
	}

	@Override
	public String getDescription() {
		return "Downloads one or more specified images.";
	}

	private void downloadAll(String collectionId) throws IOException {
		List<Image> images = imageRepository.getImagesByCollection(collectionId);
		sort(images);
		for (Image image : images) {
			download(collectionId, image);
		}
	}

	private void download(String collectionId, Image image) throws IOException {
		for (ImageType imageType : ImageType.values()) {
			try {
				out.printf("Downloading %s ... ", image.getUrl(imageType));
				out.flush();
				imageRepository.downloadImage(collectionId, image.getId(), imageType);
				out.println("Done.");
				out.flush();
			} catch (FileNotFoundException ex) {
				warning(LOG, ex.toString());
			}
		}
	}
}
