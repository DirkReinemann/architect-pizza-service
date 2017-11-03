package com.blogspot.direinem.application.user;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.blogspot.direinem.application.AbstractController;
import com.blogspot.direinem.domain.model.User;
import com.blogspot.direinem.infrastructure.configuration.ConfigurationProperties;
import com.blogspot.direinem.infrastructure.encryption.ShaEncryptor;
import com.blogspot.direinem.infrastructure.repository.UserRepository;

/**
 * Represents the controller of the registration view. Registers new users and
 * saves them to the database.
 *
 * @author Dirk Reinemann
 */
@Named
@RequestScoped
public class RegistrationController extends AbstractController {

	private static final Logger LOG = Logger.getLogger(RegistrationController.class);

	public static final String REGISTRATION_STATE_FAILED = "REGISTRATION_STATE_FAILED";

	@Inject
	private ConfigurationProperties configurationProperties;
	@Inject
	private ShaEncryptor shaEncryptor;
	@Inject
	private UserRepository userRepository;

	private String firstname;
	private String lastname;
	private String street;
	private String zip;
	private String phone;
	private String email;
	private String password;
	private String confirmPassword;
	private String state;

	/**
	 * Returns the first name of the user.
	 *
	 * @return the first name of the user
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Sets the first name of the user.
	 *
	 * @param firstname the first name of the user
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
	 * Returns the street of the user.
	 *
	 * @return the street of the user
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Sets the street of the user.
	 *
	 * @param street the street of the user
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Returns the zip code of the user.
	 *
	 * @return the zip code of the user
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * Sets the zip code of the user.
	 *
	 * @param zip the zip code of the user
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * Returns the phone number of the user.
	 *
	 * @return the phone number of the user
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the phone number of the user.
	 *
	 * @param phone the phone number of the user
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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
	 * Returns the password of the user.
	 *
	 * @return the password of the user
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of the user.
	 *
	 * @param password the password of the user
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns the confirmation password of the user.
	 *
	 * @return the confirmation password of the user
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * Sets the confirmation password of the user.
	 *
	 * @param confirmPassword the confirmation password of the user
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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
	 * Creates an user object from the given data and encrypts the password.
	 * Saves the user in the database and does the login process. Redirects to
	 * the main view.
	 *
	 * @return the name of the main view on success or null on failure
	 */
	public String register() {
		String result = null;
		try {
			User user = new User(email, shaEncryptor.encrypt(password), firstname, lastname, street, zip, phone);
			userRepository.saveUser(user);

			getHttpServletRequest().login(email, password);
			result = "index.xhtml";
		}
		catch (Exception e) {
			LOG.error("Exception", e);
			this.state = configurationProperties.getStateMessage(REGISTRATION_STATE_FAILED);
		}
		return result;
	}
}
