package com.blogspot.direinem.infrastructure.exception;

/**
 * Represents an exception for the deletion of an ingredient that is associated
 * with a pizza. Encapsulates the attribute for the primary key of ingredient.
 *
 * @author Dirk Reinemann
 */
public final class DeleteIngredientException extends Exception {

	private static final long serialVersionUID = 79824155118948050L;

	private static final String MESSAGE = ";Ingredient is asscociated with pizzas!;";

	private int idIngredient;

	/**
	 * Constructs an object with the given primary key of the ingredient and
	 * the MESSAGE.
	 *
	 * @param idIngredient the primary key of the ingredient
	 */
	public DeleteIngredientException(int idIngredient) {
		super(MESSAGE + idIngredient + ";");

		this.idIngredient = idIngredient;
	}

	/**
	 * Returns the primary key of the ingredient.
	 *
	 * @return the primary key of the ingredient
	 */
	public int getIdIngredient() {
		return idIngredient;
	}
}
