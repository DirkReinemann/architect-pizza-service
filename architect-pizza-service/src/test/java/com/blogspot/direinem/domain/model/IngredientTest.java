package com.blogspot.direinem.domain.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class IngredientTest {

	@Test
	public void createIngredientAndCheckAttributes() {
		Category category = new Category("name");
		Ingredient ingredient = new Ingredient(category, "name", 1.0);

		assertEquals("name", ingredient.getCategory().getName());
		assertEquals(0, ingredient.getId());
		assertEquals("name", ingredient.getName());
		assertEquals(1.0, ingredient.getPrice(), 0.0);
	}

	@Test
	public void createIngredientAndChangeAttributes() {
		Category category = new Category("name");
		Ingredient ingredient = new Ingredient(category, "name", 1.0);

		Category newCategory = new Category("newName");
		ingredient.setCategory(newCategory);
		ingredient.setName("newName");
		ingredient.setPrice(2.0);

		assertEquals("newName", ingredient.getCategory().getName());
		assertEquals("newName", ingredient.getName());
		assertEquals(2.0, ingredient.getPrice(), 0.0);
	}
}
