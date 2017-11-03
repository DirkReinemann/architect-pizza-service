package com.blogspot.direinem.domain.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.blogspot.direinem.domain.model.Category;
import com.blogspot.direinem.domain.model.Ingredient;
import com.blogspot.direinem.domain.model.Order;
import com.blogspot.direinem.domain.model.Pizza;
import com.blogspot.direinem.infrastructure.repository.PizzaRepository;

/**
 * Represents the cart of the user. Handles the active order, temporary pizza
 * and ingredients for the configurator.
 *
 * @author Dirk Reinemann
 */
@Stateless
public class CartService {

	@EJB
	private PizzaRepository pizzaRepository;

	private List<Category> categories;
	private List<Ingredient> ingredients;

	private Pizza pizza = new Pizza();
	private Order order = new Order();

	/**
	 * Loads all categories from the database to a local list if they doesn't
	 * exist.
	 *
	 * @return the list of all categories
	 */
	public List<Category> getCategories() {
		if (categories == null) {
			categories = pizzaRepository.getAllCategories();
		}
		return categories;
	}

	public List<Ingredient> getIngredients() {
		if (ingredients == null) {
			ingredients = pizzaRepository.getAllIngredients();
		}
		return ingredients;
	}

	/**
	 * Returns the temporary pizza object for the configurator.
	 *
	 * @return the temporary pizza object
	 */
	public Pizza getPizza() {
		return pizza;
	}

	/**
	 * Sets the temporary pizza object to the given value.
	 *
	 * @param pizza
	 *            the pizza to use for the configurator
	 */
	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}

	/**
	 * Returns the active order of the user.
	 *
	 * @return the active order of the user
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * Creates an empty temporary pizza object.
	 */
	public void newPizza() {
		this.pizza = new Pizza();
	}

	/**
	 * Cretaes an empty active order object.
	 */
	public void newOrder() {
		this.order = new Order();
	}
}
