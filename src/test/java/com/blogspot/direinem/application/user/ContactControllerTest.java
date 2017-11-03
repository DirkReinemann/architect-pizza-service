package com.blogspot.direinem.application.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Properties;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
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
import com.blogspot.direinem.infrastructure.configuration.ConfigurationProperties;
import com.blogspot.direinem.infrastructure.producer.FacesContextProducer;
import com.blogspot.direinem.infrastructure.repository.UserRepository;

/**
 * Represents the test class of the @ContactController.
 *
 * @author Dirk Reinemann
 */
@RunWith(ApplicationComposer.class)
public final class ContactControllerTest extends AbstractTest {

	@Inject
	private ContactController contactController;
	@Inject
	private ConfigurationProperties configurationProperties;

	@Mock
	private HttpServletRequest httpServletRequest;
	@Mock
	private ExternalContext externalContext;
	@Mock
	private FacesContext facesContext;
	@Mock
	private FacesContextProducer facesContextProducer;

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
		beans.addManagedClass(ContactController.class);
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
	public Properties getAdditionalProperties() {
		return null;
	}

	@Override
	public List<Class<?>> getPersistenceClasses() {
		return null;
	}

	/**
	 * Sends the mail with the help of the order controller and checks the state
	 * of the controller afterwards.
	 */
	@Test
	public void sendMailSuccessful() {
		when(facesContextProducer.getFacesContext()).thenReturn(facesContext);
		when(facesContext.getExternalContext()).thenReturn(externalContext);
		when(externalContext.getRequest()).thenReturn(httpServletRequest);
		when(httpServletRequest.getUserPrincipal()).thenReturn(null);

		contactController.sendMail();
		assertEquals(configurationProperties.getStateMessage(ContactController.MAIL_STATE_DELIVERED), contactController.getState());
	}
}
