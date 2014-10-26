package com.blogspot.direinem.application.admin;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.RollbackException;

import org.apache.log4j.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

import com.blogspot.direinem.application.AbstractController;
import com.blogspot.direinem.domain.model.Category;
import com.blogspot.direinem.domain.model.Ingredient;
import com.blogspot.direinem.infrastructure.component.CachedList;
import com.blogspot.direinem.infrastructure.component.UpdateableList;
import com.blogspot.direinem.infrastructure.exception.DeleteIngredientException;
import com.blogspot.direinem.infrastructure.repository.PizzaRepository;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * Represents the controller of the ingredient view and handles the delete, create
 * and update behavior. The scope of this bean is conversation because of the
 * interaction between two requests.
 *
 * @author Dirk Reinemann
 */
@Named
@ConversationScoped
public class IngredientController extends AbstractController implements Serializable {

	private static final long serialVersionUID = -599173656821972306L;

	private static final Logger LOG = Logger.getLogger(IngredientController.class);

	@Inject
	private transient Conversation conversation;
	@Inject
	private transient PizzaRepository pizzaRepository;

	private List<Category> categories = null;
	private transient UpdateableList<Ingredient> ingredients = null;

	private String name;
	private double price;
	private long idCategory;

	/**
	 * Initializes the list of categories and ingredients with the data from the
	 * database. Creates a comparator for the cached ingredient list.
	 */
	@PostConstruct
	private void init() {
		this.categories = pizzaRepository.getAllCategories();
		this.ingredients = new CachedList<Ingredient>(pizzaRepository.getAllIngredients()) {

			/**
			 * Compares the id of the given ingredient with the given id.
			 *
			 * @param id the id to compare
			 * @param object the ingredient to compare
			 * @return true if the id values are equal, false if not
			 */
			@Override
			protected boolean compare(Object id, Ingredient object) {
				boolean result = false;
				if (object.getId() == (Integer) id) {
					result = true;
				}
				return result;
			}
		};

		clearValues();
	}

	/**
	 * Returns the list of all ingredients.
	 *
	 * @return the list of ingredients
	 */
	public List<Ingredient> getIngredients() {
		return ingredients.getList();
	}

	/**
	 * Returns the list of all categories.
	 *
	 * @return the list of categories
	 */
	public List<Category> getCategories() {
		return categories;
	}

	/**
	 * Returns the name of the ingredient.
	 *
	 * @return the name of the ingredient
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the ingredient.
	 *
	 * @param name the name of the ingredient
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the price of the ingredient.
	 *
	 * @return the price of the ingredient
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Sets the price of the ingredient.
	 *
	 * @param price the price of the ingredient.
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Returns the id of the category.
	 *
	 * @return the id of the category
	 */
	public long getIdCategory() {
		return idCategory;
	}

	/**
	 * Sets the id of the category.
	 *
	 * @param idCategory the id of the category
	 */
	public void setIdCategory(long idCategory) {
		this.idCategory = idCategory;
	}

	/**
	 * Creates an ingredient object from the name and price values. Loads the
	 * category object from the database and puts it to the ingredient object.
	 * Saves the ingredient in the database and puts it to the local list of
	 * ingredients.
	 */
	public void createIngredient() {
		try {
			Category category = pizzaRepository.getCategory(idCategory);
			Ingredient ingredient = new Ingredient(category, name, price);
			pizzaRepository.saveIngredient(ingredient);
			ingredients.put(ingredient);
			clearValues();
		}
		catch (Exception e) {
			LOG.error("Exception", e);
		}
	}

	/**
	 * Gets the ingredient object from the local list of ingredients. Loads the
	 * category object from the database. Changes the price, name and category
	 * value of the ingredients. Updates the ingredients in the database and
	 * changes the updateable state of the ingredient to false. Ends the
	 * conversation.
	 *
	 * @param idIngredient the ingredient id
	 */
	public void updateIngredient(int idIngredient) {
		try {
			Ingredient ingredient = ingredients.get(idIngredient);
			Category category = pizzaRepository.getCategory(idCategory);
			ingredient.setPrice(price);
			ingredient.setCategory(category);
			pizzaRepository.updateIngredient(ingredient);
			ingredients.setUpdateable(idIngredient, Boolean.FALSE);
			conversation.end();
		}
		catch (Exception e) {
			LOG.error("Exception", e);
		}
	}

	/**
	 * Deletes the ingredient with the given id from the database and removes it
	 * from the local list of ingredients.
	 *
	 * @param idIngredient the ingredient id
	 * @throws DeleteIngredientException the exception for errors while deleting
	 */
	public void deleteIngredient(int idIngredient) throws DeleteIngredientException {
		try {
			pizzaRepository.deleteIngredient(idIngredient);
			ingredients.remove(idIngredient);
		}
		catch (Exception e) {
			if (e.getCause() instanceof RollbackException) {
				RollbackException r = (RollbackException) e.getCause();

				if (r.getCause() instanceof DatabaseException) {
					DatabaseException d = (DatabaseException) r.getCause();

					if (d.getCause() instanceof MySQLIntegrityConstraintViolationException) {
						throw new DeleteIngredientException(idIngredient);
					}
				}
			}
			LOG.error("Exception", e);
		}
	}

	/**
	 * Sets the updateable state of the ingredient with the given id to false.
	 * Ends the conversation.
	 *
	 * @param idIngredient the ingredient id
	 */
	public void cancelUpdate(int idIngredient) {
		ingredients.setUpdateable(idIngredient, Boolean.FALSE);
		conversation.end();
	}

	/**
	 * Sets the updateable state of the ingredient with the given  id to true.
	 * Loads the ingredient from the local list of ingredients and puts the
	 * price and category values to the local variables. Starts the conversation.
	 *
	 * @param idIngredient
	 */
	public void setUpdateable(int idIngredient) {
		ingredients.setUpdateable(idIngredient);

		Ingredient ingredient = ingredients.get(idIngredient);
		this.price = ingredient.getPrice();
		this.idCategory = ingredient.getCategory().getId();
		conversation.begin();
	}

	/**
	 * Checks whether the ingredient with the given id is updateable.
	 *
	 * @param idIngredient the ingredient id
	 * @return true if the ingredient is updateable, false if not
	 */
	public boolean isUpdateable(int idIngredient) {
		return ingredients.isUpdateable(idIngredient);
	}

	/**
	 * Sets the local variables to their default values.
	 */
	private void clearValues() {
		this.price = 0;
		this.idCategory = 0;
		this.name = null;
	}
}
