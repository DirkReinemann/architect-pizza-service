package com.blogspot.direinem.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Represents an abstraction of a pizza. Encapsulates the attributes pizza of
 * the week and a list of ingredients. Provides functions to add and remove
 * ingredients and calculates the price.
 *
 * @author Dirk Reinemann
 */
@Entity
@Table(name = "pizza")
public class Pizza implements Serializable {

	private static final long serialVersionUID = -8507836022588267123L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private boolean week;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "pizzaingredient", joinColumns = @JoinColumn(name = "idpizza"), inverseJoinColumns = @JoinColumn(name = "idingredient"))
	private List<Ingredient> ingredients = new ArrayList<Ingredient>();

	/**
	 * Constructs an empty pizza.
	 */
	public Pizza() {
	}

	/**
	 * Constructs a pizza object with the given ingredients.
	 *
	 * @param ingredients
	 *            the ingredients of the pizza
	 */
	public Pizza(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	/**
	 * Returns the primary key of the pizza.
	 *
	 * @return the primary key of the pizza
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the primary key of the pizza.
	 *
	 * @param id
	 *            the primary key of the pizza
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns the list of all ingredients of the pizza.
	 *
	 * @return the list of all ingredients of the pizza
	 */
	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	/**
	 * Checks the list of ingredients for the existence of the given ingredient
	 * by its id. Adds the ingredient if it doesn't exist.
	 *
	 * @param ingredient
	 *            the ingredient to add to the pizza
	 */
	public void addIngredient(Ingredient ingredient) {
		if (!hasIngredient(ingredient.getId())) {
			ingredients.add(ingredient);
		}
	}

	/**
	 * Removed the ingredient with the given index in the local list of
	 * ingredients.
	 *
	 * @param index
	 */
	public void removeIngredient(int index) {
		ingredients.remove(index);
	}

	/**
	 * Replaces the ingredients if the pizza with the given ingredients.
	 *
	 * @param ingredients
	 *            the ingredients to set
	 */
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	/**
	 * Iterates all ingredients of the pizza and calculates the total amount for
	 * it.
	 *
	 * @return the price of the pizza
	 */
	public double getPrice() {
		double price = 0;
		for (Ingredient ingredient : ingredients) {
			price += ingredient.getPrice();
		}

		if (week) {
			price -= price * 0.10;
		}
		return price;
	}

	/**
	 * Returns the ingredients of the pizza as comma-seperated string.
	 *
	 * @return the ingredients of the pizza as comma-seperated string
	 */
	public String getIngredientsString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Ingredient ingredient : ingredients) {
			stringBuilder.append(", ");
			stringBuilder.append(ingredient.getName());
		}
		stringBuilder.delete(0, 2);
		return stringBuilder.toString();
	}

	/**
	 * Checks whether the pizza has any ingredients or not.
	 *
	 * @return true if the pizza has any ingredients, false if not
	 */
	public boolean hasIngredients() {
		boolean result = false;
		if (ingredients != null && ingredients.size() > 0) {
			result = true;
		}
		return result;
	}

	/**
	 * Iterates all ingredients of the pizza and puts all primary key to a
	 * separate list.
	 *
	 * @return a list of primary keys for all ingredients of the pizza
	 */
	public List<Long> getIngredientIds() {
		List<Long> ids = new ArrayList<Long>();
		for (Ingredient ingredient : ingredients) {
			ids.add(ingredient.getId());
		}
		return ids;
	}

	/**
	 * Iterates all ingredients of the pizza and checks the existence of an
	 * ingredient with the given id.
	 *
	 * @param ingredientId
	 *            the primary key of the ingredients
	 * @return true if the ingredient exists, false if not
	 */
	public boolean hasIngredient(long ingredientId) {
		for (Ingredient ingredient : ingredients) {
			if (ingredient.getId() == ingredientId) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns all attribute values of the order position in human readable
	 * format.
	 */
	@Override
	public String toString() {
		return "Pizza(id=" + id + ", week" + week + ")";
	}
}
