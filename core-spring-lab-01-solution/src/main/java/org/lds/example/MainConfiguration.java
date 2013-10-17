/*
 * Copyright (C) 2013 Intellectual Reserve, Inc. All rights reserved.
 * This notice may not be removed.
 */
package org.lds.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.lds.media.image.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
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
	 * <p/>
	 * Role is "infrastructure" because it performs a background role in the
	 * application. Application code will not interact with it directly.
	 *
	 * @return A bean for configuring support for property placeholders
	 */
	@Bean() @Role(ROLE_INFRASTRUCTURE) @DependsOn(value = "envConfig")
	static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		info(LOG, "Enabling support for property placeholders.");
		return new PropertySourcesPlaceholderConfigurer();
	}

	/**
	 * For message strings.
	 * <p/>
	 * Role is "application" by default, meaning this is a user defined bean
	 * that forms a major part of the application.
	 */
	@Bean
	MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}

	@Bean
	ImageRepository imageRepository(@Value("${repository.offline}") boolean offline) {
		ImageRepository repository = new ImageRepository();
		repository.setOffline(offline);
		return repository;
	}

	/**
	 * For console output. Return value is {@link AutoCloseable}, so Spring
	 * will handle cleanup.
	 */
	@Bean
	PrintWriter output() {
		return new PrintWriter(new OutputStreamWriter(System.out));
	}

	/**
	 * For console input. Return value is {@link AutoCloseable}, so Spring
	 * will handle cleanup.
	 */
	@Bean
	BufferedReader input() {
		return new BufferedReader(new InputStreamReader(System.in));
	}

	MainConfiguration() {
		info(LOG, "Main configuration initialized.");
	}

	/**
	 * Default profile configuration
	 */
	@Configuration("envConfig")
	@Profile("!offline")
	@PropertySource({"META-INF/spring/default.properties"})
	static class DefaultConfiguration extends BaseProfileConfiguration {
		static { info(LOG, "Loading \"default\" profile..."); }
	}

	/**
	 * Offline profile configuration. Loads "default" profile properties first,
	 * then "offline" profile properties.
	 */
	@Configuration("envConfig")
	@Profile("offline")
	@PropertySource({
		"META-INF/spring/default.properties",
		"META-INF/spring/offline.properties"})
	static class OfflineConfiguration extends BaseProfileConfiguration {
		static { info(LOG, "Loading \"offline\" profile..."); }
	}

	/**
	 * Base profile configuration class. Inject the name of the profile and
	 * prints a message to the log upon initialization to identify the active
	 * profile name.
	 */
	static abstract class BaseProfileConfiguration {

		/**
		 * Injects the active "profile.name" property from the loaded property
		 * resources.
		 */
		@Value("${profile.name}")
		private String profileName;

		/**
		 * Prints a message to the log that identifies the active profile name.
		 */
		@PostConstruct
		void init() {
			info(LOG, "The \"{0}\" profile is now active.", profileName);
		}
	}
}
