package com.blogspot.direinem.infrastructure.validation;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import com.blogspot.direinem.infrastructure.repository.UserRepository;

/**
 * Represents a validator to check whether the email address of an user already
 * exists in the database or not.
 *
 * @author Dirk Reinemann
 */
@Named
@RequestScoped
public class UniqueEmailValidator implements Validator {

	private static final String EMAIL_EXISTS_MESSAGE = "The email address already exists!";

	@EJB
	private UserRepository userRepository;

	/**
	 * Queries the database for the existence of the given email address of the
	 * user. Creates a message object with the value of EMAIL_EXISTS_MESSAGE
	 * and throws if it exists.
	 *
	 * @param context {@link Validator#validate(FacesContext, UIComponent, Object)}
	 * @param component {@link Validator#validate(FacesContext, UIComponent, Object)}
	 * @param value {@link Validator#validate(FacesContext, UIComponent, Object)}
	 *
	 */
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) {

		String email = (String) value;

		if (email != null) {
			if (userRepository.existsUser(email)) {
				FacesMessage facesMessage = new FacesMessage(EMAIL_EXISTS_MESSAGE);
				throw new ValidatorException(facesMessage);
			}
		}
	}
}
