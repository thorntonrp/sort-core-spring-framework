/*
 * Copyright (C) 2013 Intellectual Reserve, Inc. All rights reserved.
 * This notice may not be removed.
 */
package org.lds.example;

import java.util.logging.Logger;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Role;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;

import static org.lds.stack.logging.LogUtils.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;

/**
 * Main application configuration. Enables component scanning and provides
 * additional configurations sub-classes for profiles.
 *
 * @author Robert Thornton <thorntonrp@ldschurch.org>
 */
@Configuration
@ComponentScan(basePackageClasses = Main.class)
@PropertySource({"META-INF/spring/default.properties"})
public class MainConfiguration {

	/**
	 * A logger named for this class
	 */
	private static final Logger LOG = getLogger();

	static {
		info(LOG, "Main configuration loading...");
	}

	/**
	 * Provides support for property placeholder expressions in the form ${...}.
	 * <p/>
	 * <i>This Made static because it is an instance of
	 * {@link org.springframework.beans.factory.config.BeanFactoryPostProcessor},
	 * meaning it has to be available before beans with property placeholders
	 * get instantiated so it can modify their bean definitions.</i>
	 *
	 * @return A bean for configuring support for property placeholders
	 */
	@Bean @Role(ROLE_INFRASTRUCTURE)
	static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		info(LOG, "Enabling support for property placeholders.");
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	//@Role(ROLE_APPLICATION) // ROLE_APPLICATION is the default
	MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}

	MainConfiguration() {
		info(LOG, "Main configuration initialized.");
	}
}
