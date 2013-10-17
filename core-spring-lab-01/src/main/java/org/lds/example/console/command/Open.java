/*
 * Copyright (C) 2013 Intellectual Reserve, Inc. All rights reserved.
 * This notice may not be removed.
 */
package org.lds.example.console.command;

import java.awt.Desktop;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import org.lds.media.image.model.Image;
import org.lds.media.image.model.ImageType;
import org.springframework.stereotype.Component;

import static org.lds.media.image.model.ImageType.GALLERY;
import static org.lds.stack.logging.LogUtils.*;
import static org.lds.stack.utils.FileUtils.file;

/**
 *
 * @author Robert Thornton <robert.p.thornton@gmail.com>
 */
@Component
public class Open extends Command {

	private static final Logger LOG = getLogger();

	@Override
	public void execute(String... args) throws IOException {
		String collectionId = context.get("collectionId");
		if (collectionId == null) {
			Desktop.getDesktop().open(imageRepository.getBaseDirectory());
		} else if (args.length == 1) {
			Desktop.getDesktop().open(file(imageRepository.getBaseDirectory(), collectionId));
		} else {
			for (int i = 1; i < args.length; i++) {
				String imageId = args[i];
				for (ImageType imageType : ImageType.values()) {
					try {
						imageRepository.downloadImage(collectionId, imageId, imageType);
					} catch (FileNotFoundException ex) {
						warning(LOG, ex.toString());
					}
				}
				Image image = imageRepository.getImage(collectionId, imageId);
				Desktop.getDesktop().open(file(imageRepository.getBaseDirectory(),
						GALLERY.name().toLowerCase(), collectionId, image.getName()));
			}
		}
	}

	@Override
	public String getDescription() {
		return "Opens a collection or image on the user's desktop.";
	}
}
