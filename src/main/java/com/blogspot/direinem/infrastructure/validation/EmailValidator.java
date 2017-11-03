package com.blogspot.direinem.infrastructure.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Represents a validator for the correct syntax of an email address.
 *
 * @author Dirk Reinemann
 */
@FacesValidator(value = "emailValidator")
public class EmailValidator implements Validator {

	private static final String EMAIL_FORMAT_MESSAGE = "The email address is invalid!";
	private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

	/**
	 * Checks the given value against the EMAIL_REGEX. Creates a message object
	 * with the value of EMAIL_FORMAT_MESSAGE and throws it.
	 *
	 * @see Validator#validate(FacesContext, UIComponent, Object)
	 */
	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent,
			Object value) {

		String email = (String) value;

		if (email != null) {
			Pattern pattern = Pattern.compile(EMAIL_REGEX);
			Matcher matcher = pattern.matcher(email);

			if (!matcher.find()) {
				FacesMessage facesMessage = new FacesMessage(EMAIL_FORMAT_MESSAGE);
				throw new ValidatorException(facesMessage);
			}
		}
	}
}
