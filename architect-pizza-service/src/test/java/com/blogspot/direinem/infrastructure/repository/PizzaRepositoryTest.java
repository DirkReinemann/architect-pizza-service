package com.blogspot.direinem.infrastructure.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJB;

import org.apache.openejb.jee.EjbJar;
import org.apache.openejb.jee.StatelessBean;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.junit.Module;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.blogspot.direinem.AbstractTest;
import com.blogspot.direinem.domain.model.Category;
import com.blogspot.direinem.domain.model.Ingredient;
import com.blogspot.direinem.domain.model.Pizza;

/**
 * Represents the test class of the @PizzaRepository.
 *
 * @author Dirk Reinemann
 */
@RunWith(ApplicationComposer.class)
public final class PizzaRepositoryTest extends AbstractTest {

	@EJB
	private PizzaRepository pizzaRepository;

	/**
	 * Configures the OpenEJB-Module.
	 *
	 * @return the EJB-jar
	 */
	@Module
	public EjbJar ejb() {
		EjbJar ejbJar = new EjbJar();
		ejbJar.addEnterpriseBean(new StatelessBean(PizzaRepository.class));
		return ejbJar;
	}

	@Override
	protected Properties getAdditionalProperties() {
		return null;
	}

	@Override
	protected List<Class<?>> getPersistenceClasses() {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		classes.add(Pizza.class);
		classes.add(Ingredient.class);
		classes.add(Category.class);
		return classes;
	}

	/**
	 * Loads five pizzas from the empty database and checks the correct size of
	 * result list.
	 */
	@Test
	public void getRecentPizzasIsEmpty() {
		int count = 5;
		List<Pizza> pizzas = pizzaRepository.getRecentPizzas(count);
		assertTrue(pizzas.isEmpty());
	}

	/**
	 * Executes the SQL-Script to produce the necessary database state. Loads
	 * five pizzas from the database and checks the correct size of the result
	 * list.
	 *
	 * @throws Exception exception while testing
	 */
	@Test
	public void getRecentPizzasHasFiveEntries() throws Exception {
		executeSql("getRecentPizzas.sql");

		int count = 5;
		List<Pizza> pizzas = pizzaRepository.getRecentPizzas(count);
		assertEquals(count, pizzas.size());
	}

	/**
	 * Executes the SQL-Script to produce the necessary database state. Loads
	 * all categories from an empty database and and checks the correct size of
	 * result list.
	 */
	@Test
	public void getAllCategoriesIsEmpty() {
		List<Category> categories = pizzaRepository.getAllCategories();
		assertTrue(categories.isEmpty());
	}

	/**
	 * Executes the SQL-Script to produce the necessary database state. Loads
	 * all categories from an empty database and and checks the correct size of
	 * result list.
	 *
	 * @throws Exception exception while testing
	 */
	@Test
	public void getAllCategoriesHasFourEntries() throws Exception {
		executeSql("getAllCategories.sql");

		List<Category> categories = pizzaRepository.getAllCategories();
		assertEquals(4, categories.size());
	}

	/**
	 * Executes the SQL-Script to produce the necessary database state. Loads
	 * all categories from the database and checks the exisstence of the
	 * category meat in the list.
	 *
	 * @throws Exception exception while testing
	 */
	@Test
	public void getAllCategoriesHasCategoryMeat() throws Exception {
		executeSql("getAllCategories.sql");

		List<Category> categories = pizzaRepository.getAllCategories();

		String c = null;
		for (int i = 0; i < categories.size() && c == null; i++) {
			Category category = categories.get(i);
			if ("MEAT".equals(category.getName())) {
				c = category.getName();
			}
		}
		assertEquals("MEAT", c);
	}

	/**
	 * Loads all ingredients from the empty database and checks the correct
	 * size of 0.
	 */
	@Test
	public void getIngredientsByCategoryIsEmpty() {
		Map<String, List<Ingredient>> ingredientsMap = pizzaRepository.getIngredientsByCategory();
		assertTrue(ingredientsMap.isEmpty());
	}

	/**
	 * Executes the SQL-Script to produce the necessary database state. Loads
	 * the category and ingredients map from the database and verifies that
	 * the number of categories is four.
	 *
	 * @throws Exception exception while testing
	 */
	@Test
	public void getIngredientsByCategoryHasFourCategories() throws Exception {
		executeSql("getIngredientsByCategory.sql");

		Map<String, List<Ingredient>> ingredientsMap = pizzaRepository.getIngredientsByCategory();
		assertEquals(4, ingredientsMap.size());
	}

	/**
	 * Executes the SQL-Script to produce the necessary database state. Loads
	 * the category and ingredients map from the database and verifies that
	 * the number of ingredients in the category meat is five.
	 *
	 * @throws Exception exception while testing
	 */
	@Test
	public void getIngredientsByCategoryHasFiveIngredientsInCategoryMeat() throws Exception {
		executeSql("getIngredientsByCategory.sql");

		Map<String, List<Ingredient>> ingredientsMap = pizzaRepository.getIngredientsByCategory();
		assertEquals(5, ingredientsMap.get("MEAT").size());
	}

	/**
	 * Executes the SQL-Script to produce the necessary database state. Loads
	 * the category and ingredients map from the database and verifies that
	 * the number of ingredients in the category fish is four.
	 *
	 *
	 * @throws Exception exception while testing
	 */
	@Test
	public void getIngredientsByCategoryHasFourIngredientsInCategoryFish() throws Exception {
		executeSql("getIngredientsByCategory.sql");

		Map<String, List<Ingredient>> ingredientsMap = pizzaRepository.getIngredientsByCategory();
		assertEquals(4, ingredientsMap.get("FISH").size());
	}

	/**
	 * Executes the SQL-Script to produce the necessary database state. Loads
	 * the category and ingredients map from the database and verifies that
	 * the number of ingredients in the category vegetables is nine.
	 *
	 *
	 * @throws Exception exception while testing
	 */
	@Test
	public void getIngredientsByCategoryHasNineteenIngredientsInCategoryVegetables() throws Exception {
		executeSql("getIngredientsByCategory.sql");

		Map<String, List<Ingredient>> ingredientsMap = pizzaRepository.getIngredientsByCategory();
		assertEquals(19, ingredientsMap.get("VEGETABLES").size());
	}

	/**
	 * Executes the SQL-Script to produce the necessary database state. Loads
	 * the category and ingredients map from the database and verifies that
	 * the number of ingredients in the category sauces is four.
	 *
	 *
	 * @throws Exception exception while testing
	 */
	@Test
	public void getIngredientsByCategoryHasFourIngredientsInCategorySauces() throws Exception {
		executeSql("getIngredientsByCategory.sql");

		Map<String, List<Ingredient>> ingredientsMap = pizzaRepository.getIngredientsByCategory();
		assertEquals(4, ingredientsMap.get("SAUCES").size());
	}
}
