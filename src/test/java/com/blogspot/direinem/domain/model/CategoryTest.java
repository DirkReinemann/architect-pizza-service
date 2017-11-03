package com.blogspot.direinem.domain.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class CategoryTest {

	@Test
	public void createCategoryAndCheckAttributes() {
		Category category = new Category("name");
		assertEquals("name", category.getName());
	}
}
