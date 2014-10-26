package com.blogspot.direinem.application.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import com.blogspot.direinem.domain.model.Newsletter;
import com.blogspot.direinem.domain.model.Newsletter.NewsletterState;
import com.blogspot.direinem.infrastructure.configuration.ConfigurationProperties;
import com.blogspot.direinem.infrastructure.producer.FacesContextProducer;
import com.blogspot.direinem.infrastructure.repository.NewsletterRepository;

/**
 * Represents the test class of the @newsletterController.
 *
 * @author Dirk Reinemann
 */
@RunWith(ApplicationComposer.class)
public final class NewsletterControllerTest extends AbstractTest {

	@Inject
	private NewsletterController newsletterController;
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
	 * Configures the OpenEJB-Module.
	 *
	 * @return the EJB-jar
	 */
	@Module
	public EjbJar ejbJar() {
		EjbJar ejbJar = new EjbJar();
		ejbJar.addEnterpriseBean(new StatelessBean(NewsletterRepository.class));
		return ejbJar;
	}

	/**
	 * Configures the OpenWebBean-Module.
	 *
	 * @return the CDI beans
	 */
	@Module
	public Beans beans() {
		Beans beans = new Beans();
		beans.addManagedClass(NewsletterController.class);
		beans.addManagedClass(ConfigurationProperties.class);
		return beans;
	}

	@Override
	protected Properties getAdditionalProperties() {
		return null;
	}

	@Override
	protected List<Class<?>> getPersistenceClasses() {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		classes.add(Newsletter.class);
		return classes;
	}

	/**
	 * Mocks all necessary objects to get no user that is signed in. Sets the
	 * newsletter state and the email address of the user. Sends the mail and
	 * checks the state - REGISTER - of the controller afterwards.
	 */
	@Test
	public void subscribeNewsletterSucessfully() {
		when(facesContextProducer.getFacesContext()).thenReturn(facesContext);
		when(facesContext.getExternalContext()).thenReturn(externalContext);
		when(externalContext.getRequest()).thenReturn(httpServletRequest);
		when(httpServletRequest.getUserPrincipal()).thenReturn(null);

		newsletterController.setNewsletterState(NewsletterState.REGISTER);
		newsletterController.setEmail("test@test.de");
		newsletterController.send();

		assertEquals(configurationProperties.getStateMessage(NewsletterController.NEWSLETTER_STATE_REGISTER), newsletterController.getState());
	}

	/**
	 * Executes the SQL-Script to produce the necessary database state.
	 * Mocks all necessary objects to get no user that is signed in. Sets the
	 * newsletter state and the email address of the user. Sends the mail and
	 * checks the state - CANCEL - of the controller afterwards.
	 *
	 * @throws Exception {@link Exception}
	 */
	@Test
	public void unsubscribeNewsletterSucessfully() throws Exception {
		executeSql("unsubscribeNewsletterSucessfully.sql");

		when(facesContextProducer.getFacesContext()).thenReturn(facesContext);
		when(facesContext.getExternalContext()).thenReturn(externalContext);
		when(externalContext.getRequest()).thenReturn(httpServletRequest);
		when(httpServletRequest.getUserPrincipal()).thenReturn(null);

		newsletterController.setNewsletterState(NewsletterState.CANCEL);
		newsletterController.setEmail("test@test.de");
		newsletterController.send();

		assertEquals(configurationProperties.getStateMessage(NewsletterController.NEWSLETTER_STATE_CANCEL), newsletterController.getState());
	}
}
