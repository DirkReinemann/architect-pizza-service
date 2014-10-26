package com.blogspot.direinem.domain;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.blogspot.direinem.domain.model.CategoryTest;
import com.blogspot.direinem.domain.model.IngredientTest;
import com.blogspot.direinem.domain.model.OrderPositionTest;
import com.blogspot.direinem.domain.model.OrderTest;
import com.blogspot.direinem.domain.model.PizzaTest;
import com.blogspot.direinem.domain.model.UserTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CategoryTest.class,
	IngredientTest.class,
	OrderPositionTest.class,
	OrderTest.class,
	PizzaTest.class,
	UserTest.class
})
public class DomainTestSuite {

}
