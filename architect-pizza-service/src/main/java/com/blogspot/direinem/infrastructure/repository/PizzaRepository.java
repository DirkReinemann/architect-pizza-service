package com.blogspot.direinem.infrastructure.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.blogspot.direinem.domain.model.Category;
import com.blogspot.direinem.domain.model.Ingredient;
import com.blogspot.direinem.domain.model.Pizza;

/**
 * Represents a repository to access and manipulate the pizza, ingredient and
 * category objects.
 * .
 *
 * @author Dirk Reinemann
 */
@Stateless
public class PizzaRepository extends AbstractRepository implements Serializable {

    private static final long serialVersionUID = -8542302160105212449L;

    /**
	 * Loads the pizza where the flag week is 1 from the database.
	 *
	 * @return the pizza of the week
	 */
	@SuppressWarnings("unchecked")
	public Pizza getPizzaOfTheWeek() {
		List<Pizza> pizzas = entityManager.createQuery("SELECT p FROM Pizza p WHERE p.week=true").getResultList();
        return pizzas.isEmpty() ? null : pizzas.get(0);
    }

	/**
	 * Loads the given number of pizzas from the database ordered by their
	 * primary key.
	 *
	 * @param count the number of pizzas to load
	 * @return the list of pizzas
	 */
	@SuppressWarnings("unchecked")
	public List<Pizza> getRecentPizzas(int count) {
        Query query = entityManager.createQuery("SELECT p FROM Pizza p ORDER BY p.id DESC", Pizza.class);
        query.setMaxResults(count);
        return query.getResultList();
    }

	/**
	 * Loads all categories from the database.
	 *
	 * @return the list of categories
	 */
    @SuppressWarnings("unchecked")
	public List<Category> getAllCategories() {
    	return entityManager.createQuery("SELECT c FROM Category c").getResultList();
    }

    /**
     * Loads all ingredients from the database ordered by their category name.
     * Creates a map of the category and the ingredients. Iterates through the
     * ingredients and puts them into the map accordingly their category.
     *
     * @return the map of category and ingredients
     */
    @SuppressWarnings("unchecked")
	public Map<String, List<Ingredient>> getIngredientsByCategory() {
    	Query query = entityManager.createQuery("SELECT i FROM Ingredient i ORDER BY i.category.name", Ingredient.class);
    	List<Ingredient> ingredients = query.getResultList();

    	Map<String, List<Ingredient>> ingredientsMap = new HashMap<String, List<Ingredient>>();
    	int i = 0;
    	while (i < ingredients.size()) {
    		String category = ingredients.get(i).getCategory().getName();

    		List<Ingredient> ingredientList = new ArrayList<Ingredient>();
    		while (i < ingredients.size() && ingredients.get(i).getCategory().getName().equals(category)) {
    			ingredientList.add(ingredients.get(i));
    			i++;
    		}
    		ingredientsMap.put(category, ingredientList);
    	}
    	return ingredientsMap;
	}

    /**
     * Loads the ingredient with the given primary key from the database.
     *
     * @param id the primary key of the ingredient
     * @return the ingredient object
     */
    public Ingredient getIngredient(long id) {
    	return entityManager.find(Ingredient.class, id);
    }

    /**
     * Loads the pizza with the given primary key from the database.
     *
     * @param id the primary key of the pizza
     * @return the pizza object
     */
    public Pizza getPizza(long id) {
    	return entityManager.find(Pizza.class, id);
    }

    /**
     * Loads the category with the given primary key from the database.
     *
     * @param id the primary key of the category
     * @return the category object
     */
    public Category getCategory(long id) {
    	return entityManager.find(Category.class, id);
    }

    /**
     * Saves the given ingredient to the database.
     *
     * @param ingredient the ingredient to save
     */
    public void saveIngredient(Ingredient ingredient) {
    	entityManager.persist(ingredient);
    }

    /**
     * Updates the state of the given ingredient in the database.
     *
     * @param ingredient the ingredient to update
     */
    public void updateIngredient(Ingredient ingredient) {
    	entityManager.merge(ingredient);
    }

    /**
     * Deletes the ingredient with the given primary key from the database.
     *
     * @param idIngredient the primary key of the ingredient
     */
    public void deleteIngredient(int idIngredient) {
    	Ingredient ingredient = entityManager.find(Ingredient.class, idIngredient);
    	entityManager.remove(ingredient);
    }

    /**
     * Searches for an ingredient with the given name in the database.
     *
     * @param name the name of the ingredient
     * @return true if the ingredient exists, false if not
     */
    public boolean existsIngredient(String name) {
    	Query query = entityManager.createQuery("SELECT COUNT(i) FROM Ingredient i WHERE i.name=:name");
    	query.setParameter("name", name);
    	Long count = (Long) query.getSingleResult();
    	return (count == 0) ? false : true;
    }

    /**
     * Loads all ingredients from the database ordered by the name.
     *
     * @return the list of ingredients
     */
    @SuppressWarnings("unchecked")
	public List<Ingredient> getAllIngredients() {
    	return entityManager.createQuery("SELECT i FROM Ingredient i ORDER BY i.name ASC").getResultList();
    }

    /**
     * Calls the database procedure that changes the pizza of the week.
     */
    public void newPizzaOfTheWeek() {
    	entityManager.createNativeQuery("CALL generate_pizza_of_the_week()").executeUpdate();
    }
}
