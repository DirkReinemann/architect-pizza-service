package com.blogspot.direinem.application.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.apache.openejb.jee.Beans;
import org.apache.openejb.jee.EjbJar;
import org.apache.openejb.jee.StatelessBean;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.junit.MockInjector;
import org.apache.openejb.junit.Module;
import org.apache.openejb.mockito.MockitoInjector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.blogspot.direinem.AbstractTest;
import com.blogspot.direinem.domain.model.Role;
import com.blogspot.direinem.domain.model.User;
import com.blogspot.direinem.infrastructure.configuration.ConfigurationProperties;
import com.blogspot.direinem.infrastructure.encryption.ShaEncryptor;
import com.blogspot.direinem.infrastructure.producer.FacesContextProducer;
import com.blogspot.direinem.infrastructure.repository.UserRepository;

/**
 * Represents the test class of the @RegistrationController.
 *
 * @author Dirk Reinemann
 */
@RunWith(ApplicationComposer.class)
public final class RegistrationControllerTest extends AbstractTest {

	@Inject
	private RegistrationController registrationController;

	@Mock
	private ExternalContext externalContext;
	@Mock
	private FacesContext facesContext;
	@Mock
	private FacesContextProducer facesContextProducer;
	@Mock
	private HttpServletRequest httpServletRequest;

	@MockInjector
    public Class<?> mockitoInjector() {
        return MockitoInjector.class;
    }

	/**
	 * Configures the OpenWebBean-Module.
	 *
	 * @return the CDI beans
	 */
	@Module
	public Beans beans() {
		Beans beans = new Beans();
		beans.addManagedClass(RegistrationController.class);
		beans.addManagedClass(ShaEncryptor.class);
		beans.addManagedClass(ConfigurationProperties.class);
		return beans;
	}

	/**
	 * Configures the OpenEJB-Module.
	 *
	 * @return the EJB-jar
	 */
	@Module
	public EjbJar ejbs() {
		EjbJar ejbJar = new EjbJar();
		ejbJar.addEnterpriseBean(new StatelessBean(UserRepository.class));
		return ejbJar;
	}

	@Override
	protected Properties getAdditionalProperties() {
		return null;
	}

	@Override
	protected List<Class<?>> getPersistenceClasses() {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		classes.add(User.class);
		classes.add(Role.class);
		return classes;
	}

	/**
	 * Mocks all necessary objects to get no request. Sets all values of the
	 * user and registers the user in the application. Checks the correct
	 * view name as return value and the values of the user afterwards.
	 */
	@Test
	public void registerSuccessful() {
		when(facesContextProducer.getFacesContext()).thenReturn(facesContext);
		when(facesContext.getExternalContext()).thenReturn(externalContext);
		when(externalContext.getRequest()).thenReturn(httpServletRequest);

		registrationController.setEmail("test@test.de");
		registrationController.setFirstname("test");
		registrationController.setLastname("test");
		registrationController.setPassword("123456");
		registrationController.setPhone("1234565789");
		registrationController.setStreet("teststrasse 13");
		registrationController.setZip("12345");

		assertEquals("index.xhtml", registrationController.register());

		Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.email=:email");
		query.setParameter("email", "test@test.de");
		User user = (User) query.getSingleResult();

		assertNotNull(user);
		assertEquals("test", user.getFirstname());
		assertEquals("test", user.getLastname());
		assertEquals("1234565789", user.getPhone());
		assertEquals("teststrasse 13", user.getStreet());
		assertEquals("12345", user.getZip());
		assertEquals(User.Role.USER, user.getRole());
	}
}
