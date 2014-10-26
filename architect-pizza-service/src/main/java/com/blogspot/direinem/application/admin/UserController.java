package com.blogspot.direinem.application.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.blogspot.direinem.application.AbstractController;
import com.blogspot.direinem.domain.model.User;
import com.blogspot.direinem.infrastructure.encryption.ShaEncryptor;
import com.blogspot.direinem.infrastructure.exception.DeleteAdminException;
import com.blogspot.direinem.infrastructure.repository.UserRepository;

/**
 * Represents the controller of the user management view. Creates and deletes
 * users according the role of the current user.
 *
 * @author Dirk Reinemann
 */
@Named
@RequestScoped
public class UserController extends AbstractController {

	private static final Logger LOG = Logger.getLogger(UserController.class);

	@Inject
	private ShaEncryptor shaEncryptor;
	@Inject
	private UserRepository userRepository;

	private List<String> roles = null;
	private List<User> users = null;

	private String username;
	private String password;
	private String confirmPassword;
	private String role;

	/**
	 * Loads the users from the database. If the current user is an administrator,
	 * the administrator users are loaded. If the current user is an employee, the
	 * employee users are loaded. Initializes the list of roles for the new users
	 * according the role of the current user.
	 */
	@PostConstruct
	public void init() {
		if (isAdmin()) {
			users = userRepository.getAdminsAndEmployees();
		}
		else {
			users = userRepository.getEmployees();
		}

		roles = new ArrayList<String>();
		if (isAdmin()) {
			roles.add(User.Role.ADMIN.name());
		}
		roles.add(User.Role.EMPLOYEE.name());
	}

	/**
	 * Returns the name of the user.
	 *
	 * @return the name of the user
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the name of the user.
	 *
	 * @param username the name of the user
	 */
	public void setUsername(String username) {
		this.username = username;
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
	 * Returns the confirmed password of the user.
	 *
	 * @return the confirmed password of the user
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * Sets the confirmed password of the user.
	 *
	 * @param confirmPassword the confirmed password of the user
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * Returns the role of the user.
	 *
	 * @return the role of the user
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the role of the user.
	 *
	 * @param role the role of the user
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Returns the list of all roles.
	 *
	 * @return the list of roles
	 */
	public List<String> getRoles() {
		return roles;
	}

	/**
	 * Returns the list of users.
	 *
	 * @return the list of users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * Creates an user object from the role, name and encrypted password. Saves
	 * the user to the database and sets the values of the variables to their
	 * default values. Adds the user to the local list of users.
	 */
	public void createUser() {
		try {
			User user = new User(User.Role.valueOf(role), username, shaEncryptor.encrypt(password));
			userRepository.saveUser(user);
			this.username = null;
			this.role = null;
			users.add(user);
		}
		catch (Exception e) {
			LOG.error("Exception", e);
		}
	}

	/**
	 * Deletes the user with the given id from the database. Checks the necessary
	 * rights of the current user to do this and deletes the user from the
	 * local list of users.
	 *
	 * @param idUser the id of the user
	 * @throws DeleteAdminException the error while deleting the user
	 */
	public void deleteUser(int idUser) throws DeleteAdminException {
		try {
			User user = getUser(idUser);

			if (user.isInRole(User.Role.ADMIN) && userRepository.getAdminCount() == 1) {
				throw new DeleteAdminException(idUser);
			}
			else {
				userRepository.deleteUser(idUser);
				users.remove(user);
			}
		}
		catch (DeleteAdminException e) {
			throw e;
		}
		catch (Exception e) {
			LOG.error("Exception", e);
		}
	}

	/**
	 * Searches for the user with the given id in the local list of users and
	 * returns it on success.
	 *
	 * @param idUser the id of the user
	 * @return the user if it was found, null if not
	 */
	private User getUser(int idUser) {
		User result = null;
		for (User user : users) {
			if (user.getId() == idUser) {
				result = user;
				break;
			}
		}
		return result;
	}
}
