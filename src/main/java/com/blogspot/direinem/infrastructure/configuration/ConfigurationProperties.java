package com.blogspot.direinem.infrastructure.configuration;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

/**
 * Represents a properties container for all properties that are mapped in the
 * CONFIGURATION_FILE. All properties can be accessed directly by functions.
 *
 * @author Dirk Reinemann
 */
@Named
@ApplicationScoped
public class ConfigurationProperties {

	private static final Logger LOG = Logger.getLogger(ConfigurationProperties.class);
	private static final String CONFIGURATION_FILE = "Configuration.properties";

	private Configuration configuration;

	/**
	 * Constructs an object with access to the CONFIGURATION_FILE.
	 */
	public ConfigurationProperties() {
		 try {
			configuration = new PropertiesConfiguration(CONFIGURATION_FILE);
		}
		catch (ConfigurationException e) {
			LOG.error("ConfigurationException", e);
		}
	}

	/**
	 * Returns the value for the recent pizzas that are displayed on the main
	 * view.
	 *
	 * @return the number of recent pizzas
	 */
	public synchronized int getRecentPizzaCount() {
		return configuration.getInt("RECENT_PIZZA_COUNT");
	}

	public synchronized int getRecentOrderCount() {
		return configuration.getInt("RECENT_ORDER_COUNT");
	}

	/**
	 * Returns an informational message for the user by the given key in the
	 * properties file.
	 *
	 * @param key the key for the message
	 * @return the message
	 */
	public synchronized String getStateMessage(String key) {
		return configuration.getString(key);
	}
}
