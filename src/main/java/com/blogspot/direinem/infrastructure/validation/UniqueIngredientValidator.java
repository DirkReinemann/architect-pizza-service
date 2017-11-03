package com.blogspot.direinem.infrastructure.validation;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import com.blogspot.direinem.infrastructure.repository.PizzaRepository;

/**
 * Represents a validator to check whether the ingredient already exists in the
 * database or not.
 *
 * @author Dirk Reinemann
 */
@Named
@RequestScoped
public class UniqueIngredientValidator implements Validator {

	private static final String INGREDIENT_EXISTS_MESSAGE = "This ingredient already exists!";

	@EJB
	private PizzaRepository pizzaRepository;

	/**
	 * Queries the database for the existence of the given ingredient name.
	 * Creates a message object with the value of INGREDIENT_EXISTS_MESSAGE
	 * and throws if it exists.
	 *
	 * @see Validator#validate(FacesContext, UIComponent, Object)
	 */
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) {

		String name = (String) value;

		if (name != null) {
			if (pizzaRepository.existsIngredient(name)) {
				FacesMessage facesMessage = new FacesMessage(INGREDIENT_EXISTS_MESSAGE);
				throw new ValidatorException(facesMessage);
			}
		}
	}
}
