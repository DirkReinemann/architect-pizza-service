package com.blogspot.direinem.application.user;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.blogspot.direinem.application.AbstractController;
import com.blogspot.direinem.domain.model.Newsletter.NewsletterState;
import com.blogspot.direinem.infrastructure.configuration.ConfigurationProperties;
import com.blogspot.direinem.infrastructure.repository.NewsletterRepository;

/**
 * Represents the controller of the newsletter view. Subscribes and unsubscribes
 * a user from the newsletter.
 *
 * @author Dirk Reinemann
 */
@Named
@RequestScoped
public class NewsletterController extends AbstractController {

	private static final Logger LOG = Logger.getLogger(NewsletterController.class);

	public static final String NEWSLETTER_STATE_REGISTER = "NEWSLETTER_STATE_REGISTER";
	public static final String NEWSLETTER_STATE_CANCEL = "NEWSLETTER_STATE_CANCEL";

	@Inject
	private ConfigurationProperties configurationProperties;
	@Inject
	private NewsletterRepository newsletterRepository;

	private String email;
	private NewsletterState newsletterState;
	private String state;

	/**
	 * Returns the newsletter states - register and cancel.
	 *
	 * @return the newsletter states
	 */
	public NewsletterState[] getNewsletterStates() {
		return NewsletterState.values();
	}

	/**
	 * Returns the email address of the user.
	 *
	 * @return the email address of the user
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email address of the user.
	 *
	 * @param email the email address of the user
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns the newsletter state.
	 *
	 * @return the newsletter state
	 */
	public NewsletterState getNewsletterState() {
		return newsletterState;
	}

	/**
	 * Sets the newsletter state.
	 *
	 * @param newsletterState the newsletter state
	 */
	public void setNewsletterState(NewsletterState newsletterState) {
		this.newsletterState = newsletterState;
	}

	/**
	 * Returns the state of the process.
	 *
	 * @return the state of the process
	 */
	public String getState() {
		return state;
	}

	/**
	 * Saves the email address of the user and the newsletter state in the
	 * database.
	 */
	public void send() {
		if (getCurrentUsername() != null) {
			this.email = getCurrentUsername();
		}

		try {
			newsletterRepository.saveNewsletter(email, newsletterState);
			switch (newsletterState) {
			case REGISTER:
				this.state = configurationProperties.getStateMessage(NEWSLETTER_STATE_REGISTER);
				break;
			case CANCEL:
				this.state = configurationProperties.getStateMessage(NEWSLETTER_STATE_CANCEL);
				break;
			}
		}
		catch (Exception e) {
			LOG.error("send(): Exception", e);
		}
	}
}
