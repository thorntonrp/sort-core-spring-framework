/*
 * Copyright (C) 2013 Intellectual Reserve, Inc. All rights reserved.
 * This notice may not be removed.
 */
package org.lds.example.console.command;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import org.lds.media.image.model.Image;
import org.lds.media.image.model.ImageType;
import org.springframework.stereotype.Component;

import static org.lds.stack.logging.LogUtils.*;

/**
 *
 * @author Robert Thornton <robert.p.thornton@gmail.com>
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
					download(collectionId, args[i]);
				}
			}
		}
	}

	@Override
	public String getDescription() {
		return "Downloads one or more specified images.";
	}

	private void downloadAll(String collectionId) throws IOException {
		for (Image image : imageRepository.getImagesByCollection(collectionId)) {
			download(collectionId, image.getId());
		}
	}

	private void download(String collectionId, String imageId) throws IOException {
		for (ImageType imageType : ImageType.values()) {
			try {
				imageRepository.downloadImage(collectionId, imageId, imageType);
			} catch (FileNotFoundException ex) {
				warning(LOG, ex.toString());
			}
		}
	}
}
