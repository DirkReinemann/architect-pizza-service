package com.blogspot.direinem.domain.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents an abstraction of a pizza category. Encapsulates the attribute
 * name.
 *
 * @author Dirk Reinemann
 */
@Entity
@Table(name = "category")
public class Category implements Serializable {

	private static final long serialVersionUID = 5074801135462032124L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;

	/**
	 * Required for Java Persitence API.
	 */
	protected Category() {
	}

	/**
	 * Creates a new category instance with the given name.
	 *
	 * @param name the name of the category
	 */
	public Category(String name) {
		this.name = name;
	}

	/**
	 * Returns the primary key of the category.
	 *
	 * @return the primary key of the category
	 */
	public long getId() {
		return id;
	}

	/**
	 * Returns the name of the category.
	 *
	 * @return the name of the category
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the category.
	 *
	 * @param name the name of the category
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns all attribute values of the category in human readable format.
	 */
	@Override
	public String toString() {
		return "Category(id=" + id + ", name=" + name + ")";
	}
}
