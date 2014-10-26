package com.blogspot.direinem.application;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.blogspot.direinem.infrastructure.configuration.ConfigurationProperties;

/**
 * Represents the controller for the authentication mechanisms. Signs users in
 * and out.
 *
 * @author Dirk Reinemann
 */
@Named
@RequestScoped
public class AuthenticationController extends AbstractController {

	private static final Logger LOG = Logger.getLogger(AuthenticationController.class);

	public static final String LOGIN_STATE_FAILED = "LOGIN_STATE_FAILED";
	public static final String LOGOUT_STATE_FAILED = "LOGOUT_STATE_FAILED";

	@Inject
	private ConfigurationProperties configurationProperties;

	private String email;
	private String password;
	private String state;

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
	 * Returns the password of the current user.
	 *
	 * @return the password of the current user
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of the current user.
	 *
	 * @param password the password of the current user
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * Signs the user with the given user name and password in. Redirects the
	 * user the administrator view if the user is an administrator or employee
	 * or to the main view if the user is a customer.
	 *
	 * @return the name of the administrator view or main view on success or null
	 * on failure
	 */
	public String login() {
		String result = null;

		HttpServletRequest httpServletRequest = getHttpServletRequest();
		try {
			httpServletRequest.login(email, password);

			if (isAdmin() || isEmployee()) {
				result = "user.xhtml";
			}
			else {
				result = "index.xhtml";
			}
		}
		catch (ServletException e) {
			LOG.error("ServletException", e);
			this.state = configurationProperties.getStateMessage(LOGIN_STATE_FAILED);
		}
		return result;
	}

	/**
	 * Signs the current user out. Redirects to the main view.
	 *
	 * @return the name of the main view on success or null failure
	 */
	public String logout() {
		String result = null;

		HttpServletRequest httpServletRequest = getHttpServletRequest();
		try {
			httpServletRequest.logout();
			result = "index.xhtml";
		}
		catch (ServletException e) {
			LOG.error("ServletException", e);
			this.state = configurationProperties.getStateMessage(LOGOUT_STATE_FAILED);
		}
		return result;
	}
}
