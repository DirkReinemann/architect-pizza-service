package com.blogspot.direinem.application;

import java.security.Principal;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.blogspot.direinem.domain.model.User;

/**
 * Represents an abstract controller with functionality that is needed by other
 * controllers in the application. Provides direct access to the @FacesContext,
 * {@link HttpServletRequest} and name of the current user.
 *
 * @author Dirk Reinemann
 */
public abstract class AbstractController {

	@Inject
	protected FacesContext facesContext;

	/**
	 * Returns the instance of the current @HttpServletRequest.
	 *
	 * @return the instance of the current @HttpServletRequest
	 */
	protected HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) facesContext.getExternalContext().getRequest();
	}

	/**
	 * Returns the name of the current user.
	 *
	 * @return the name of the current user if present or null if not
	 */
	protected String getCurrentUsername() {
		String result = null;

		Principal principal = getHttpServletRequest().getUserPrincipal();
		if (principal != null) {
			result = principal.getName();
		}
		return result;
	}

	/**
	 * Checks whether the current user is an employee or not.
	 *
	 * @return true if the current user is an employee, false if not
	 */
	public boolean isEmployee() {
		return (getHttpServletRequest().isUserInRole(User.Role.EMPLOYEE.name())) ? true : false;
	}

	/**
	 * Checks whether the current user is an administrator or not.
	 *
	 * @return true if the current user is an administrator, false if not
	 */
	public boolean isAdmin() {
		return (getHttpServletRequest().isUserInRole(User.Role.ADMIN.name())) ? true : false;
	}

	/**
	 * Checks whether a user is signed in to the application or not.
	 *
	 * @return true if a user is signed in, false if not
	 */
	public boolean isLoggedIn() {
		return (getCurrentUsername() == null) ? false : true;
	}
}
