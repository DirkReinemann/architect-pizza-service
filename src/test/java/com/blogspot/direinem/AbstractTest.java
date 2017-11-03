package com.blogspot.direinem;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.apache.openejb.jee.jpa.unit.PersistenceUnit;
import org.apache.openejb.junit.Configuration;
import org.apache.openejb.junit.Module;

/**
 * Represents an abstract test case that provides some default configurations.
 * Gives the user a persistence context with an underlying in-memory hsqldb.
 * Additional configuration properties or classes for the persistence can be
 * added through abstract methods.
 *
 * @author Dirk Reinemann
 */
public abstract class AbstractTest {

	@PersistenceContext
	protected EntityManager entityManager;

	@Resource
	protected UserTransaction userTransaction;

	/**
	 * Initializes the OpenEJB context for the test environment. All configuration belong to a
	 * property object that is given to the EJB container.
	 *
	 * @return Properties the default test environment properties
	 */
	@Configuration
	public final Properties configuration() {
		Properties properties = new Properties();

		properties.put("java.naming.factory.initial", "org.apache.openejb.client.LocalInitialContextFactory");

		properties.put("ArchitectPizzaTest", "new://Resource?type=DataSource");
		properties.put("ArchitectPizzaTest.JdbcDriver", "org.hsqldb.jdbcDriver");
		properties.put("ArchitectPizzaTest.JdbcUrl", "jdbc:hsqldb:mem:architectpizza;encoding=UTF-8;shutdown=true");

		properties.put("javax.persistence.provider", "org.eclipse.persistence.jpa.PersistenceProvider");
		properties.put("architectpizza.eclipselink.target-database", "org.eclipse.persistence.platform.database.HSQLPlatform");
		properties.put("eclipselink.ddl-generation", "create-tables");
		properties.put("eclipselink.ddl-generation.output-mode", "database");

		Properties props = getAdditionalProperties();
		if (props != null) {
			properties.putAll(props);
		}

		return properties;
	}

	/**
	 * Configures the persistence unit for the test classes. Provides access to
	 * the database configured in the Server-Container. Adds all classes that
	 * are defined by the abstract method in the respective test class.
	 *
	 * @return the persistence unit for database access
	 */
	@Module
	public final PersistenceUnit persistence() {
		PersistenceUnit persistenceUnit = new PersistenceUnit("architectpizza");
		persistenceUnit.setJtaDataSource("ArchitectPizza");

		List<Class<?>> classes = getPersistenceClasses();
		if (classes != null) {
			for (Class<?> c : classes) {
				persistenceUnit.addClass(c);
			}
		}
		return persistenceUnit;
	}

	/**
	 * Possibility to add extra properties to the configuration by a test class.
	 *
	 * @return the additional property values
	 */
	protected abstract Properties getAdditionalProperties();

	/**
	 * Possibility to add persistence classes to the used persistence unit by
	 * a test class.
	 *
	 * @return the entity classes to manage
	 */
	protected abstract List<Class<?>> getPersistenceClasses();

	/**
	 * Executes a SQL script with the given filename line by line. Ignores
	 * empty lines.
	 *
	 * @param filename the name of the SQL-Script
	 *
	 * @throws Exception {@link Exception}
	 */
	protected void executeSql(final String filename) throws Exception {
		userTransaction.begin();

		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(ClassLoader.getSystemClassLoader().getResourceAsStream(filename))));

			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if (!line.trim().isEmpty()) {
					entityManager.createNativeQuery(line).executeUpdate();
				}
			}
		}
		finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (userTransaction.getStatus() != Status.STATUS_NO_TRANSACTION) {
				userTransaction.commit();
			}
		}
	}
}
