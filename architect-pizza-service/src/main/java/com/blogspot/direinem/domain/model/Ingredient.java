package com.blogspot.direinem.domain.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Represents an abstraction of a pizza ingredient. Encapsulates the attributes
 * primary key, name and price. Has a many to one relationship to the pizza category.
 * All attributes are required and must handed on construction.
 *
 * @author Dirk Reinemann
 */
@Entity
@Table(name = "ingredient")
public class Ingredient implements Serializable {

	private static final long serialVersionUID = -7397719401404576335L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private double price;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idcategory")
	private Category category;

	/**
	 * Required for Java Persistence API.
	 */
	protected Ingredient() {
	}

	/**
	 * Creates an ingredient instance with the given name and price and category.
	 *
	 * @param name the name of the ingredient
	 * @param price the price of the ingredient
	 */
	public Ingredient(Category category, String name, double price) {
		this.category = category;
		this.name = name;
		this.price = price;
	}

	/**
	 * Returns the primary key of the ingredient.
	 *
	 * @return the primary key of the ingredient
	 */
	public long getId() {
		return id;
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
	 * @param name the name to set
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
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Returns the category object of the ingredient.
	 *
	 * @return the category object of the ingredient
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * Sets the category of the ingredient.
	 *
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * Returns all attribute values of the category in human readable format.
	 */
	@Override
	public String toString() {
		return "Ingredient(id=" + id + ", name=" + name + ", price=" + price + ")";
	}
}
