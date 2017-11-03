package com.blogspot.direinem.application.user;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.blogspot.direinem.domain.model.Pizza;
import com.blogspot.direinem.infrastructure.configuration.ConfigurationProperties;
import com.blogspot.direinem.infrastructure.repository.PizzaRepository;

/**
 * Represents the controller of the pizza view. Loads the pizza of the week and
 * the recent pizzas.
 *
 * @author Dirk Reinemann
 */
@Named
@RequestScoped
public class PizzaController {

	@Inject
	private PizzaRepository pizzaRepository;
	@Inject
	private ConfigurationProperties configurationProperties;

	private Pizza pizzaOfTheWeek;
	private List<Pizza> recentPizzas;

	/**
	 * Loads the pizza of the week and the ten recent pizzas from the database.
	 */
	@PostConstruct
	public void init() {
		pizzaOfTheWeek = pizzaRepository.getPizzaOfTheWeek();
		recentPizzas = pizzaRepository.getRecentPizzas(configurationProperties.getRecentPizzaCount());
	}

	/**
	 * Returns the pizza of the week object.
	 *
	 * @return the pizza of the week object
	 */
	public Pizza getPizzaOfTheWeek() {
		return pizzaOfTheWeek;
	}

	/**
	 * Returns the list of recent pizzas.
	 *
	 * @return the list of recent pizzas
	 */
	public List<Pizza> getRecentPizzas() {
		return recentPizzas;
	}
}
