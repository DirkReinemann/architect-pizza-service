package com.blogspot.direinem.application.user;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.blogspot.direinem.application.AbstractController;
import com.blogspot.direinem.domain.model.User;
import com.blogspot.direinem.infrastructure.configuration.ConfigurationProperties;
import com.blogspot.direinem.infrastructure.repository.UserRepository;

/**
 * Represents the controller of the contact message view. Sends messages from the
 * user as mail to the company.
 *
 * @author Dirk Reinemann
 */
@Named
@RequestScoped
public class ContactController extends AbstractController {

	public static final String MAIL_STATE_DELIVERED = "MAIL_STATE_DELIVERED";

	@Inject
	private ConfigurationProperties configurationProperties;
	@Inject
	private UserRepository userRepository;

	private String firstname;
	private String lastname;
	private String email;
	private String subject;
	private String body;
	private String state;

	/**
	 * Sets the state to null.
	 */
	@PostConstruct
	public void init() {
		if (isLoggedIn()) {
			User user = userRepository.getUserByEmail(getCurrentUsername());
			this.firstname = user.getFirstname();
			this.lastname = user.getLastname();
			this.email = user.getEmail();
		}
	}

	/**
	 * Returns the first name of the user.
	 *
	 * @return the first name
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Sets the first name of the user.
	 *
	 * @param firstname first name of the user
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Returns the last name of the user.
	 *
	 * @return the last name of the user
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Sets the last name of the user.
	 *
	 * @param lastname the last name of the user
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
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
	 * @param email the email address of the user.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns the subject of the message.
	 *
	 * @return the subject of the message
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the subject of the message.
	 *
	 * @param subject the subject of the message
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Returns the content of the message.
	 *
	 * @return the content of the message.
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Sets the content of the message.
	 *
	 * @param body the content of the message
	 */
	public void setBody(String body) {
		this.body = body;
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
	 * Sets the state of the process. Doesn't send a real message.
	 */
	public void sendMail() {
		this.state = configurationProperties.getStateMessage(MAIL_STATE_DELIVERED);
	}
}
