/*
 * Copyright (C) 2013 Intellectual Reserve, Inc. All rights reserved.
 * This notice may not be removed.
 */
package org.lds.media.image.repository;

import java.io.IOException;

/**
 *
 * @author Robert Thornton <thorntonrp@ldschurch.org>
 */
public class OfflineRepositoryException extends IOException {

	private static final long serialVersionUID = 1L;

	public OfflineRepositoryException(String message) {
		super(message);
	}
}
