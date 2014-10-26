package com.blogspot.direinem.infrastructure.validation;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

/**
 * Represents a validator for the password values of the user. Checks the
 * existence and equality of the password and the confirmed password.
 *
 * @author Dirk Reinemann
 */
@Named
@RequestScoped
public class PasswordValidator implements Validator {

	private static final String PASSWORD_MESSAGE = "Passwords not equal!";
	private static final String PASSWORD_COMPONENT_ID = "password";

	/**
	 * Checks if the given password and confirmed password values are present
	 * and equal. Creates a message object with the value of PASSWORD_MESSAGE
	 * and throws if not.
	 *
	 * @see Validator#validate(FacesContext, UIComponent, Object)
	 */
	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent,
			Object object) {

		String confirmPassword = (String) object;
		String password = getPassword(uiComponent);

		if (password != null && confirmPassword != null) {
			if (!password.equals(confirmPassword)) {
				FacesMessage facesMessage = new FacesMessage(PASSWORD_MESSAGE);
				throw new ValidatorException(facesMessage);
			}
		}
	}

	/**
	 * Iterates the object tree of the view to find the password
	 * input component with the PASSWORD_COMPONENT_ID id and returns its value.
	 * Returns null if it was not found.
	 *
	 * @param uiComponent the confirm password input component
	 * @return the password value
	 */
	private String getPassword(final UIComponent uiComponent) {
		for (UIComponent child : uiComponent.getParent().getChildren()) {
			if (child.getId().equals(PASSWORD_COMPONENT_ID)) {
				return (String) child.getAttributes().get("value");
			}
		}
		return null;
	}
}
