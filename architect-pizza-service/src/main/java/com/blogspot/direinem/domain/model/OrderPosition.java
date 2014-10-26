package com.blogspot.direinem.domain.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Represents an abstraction of an order position. Encapsulates the attributes
 * pizza amount, pizza and order.
 *
 * @author Dirk Reinemann
 */
@Entity
@Table(name = "orderposition")
public class OrderPosition implements Serializable {

	private static final long serialVersionUID = 8541951691865798826L;

	private static final int MAX_AMOUNT = 10;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private int amount = 1;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "idpizza")
	private Pizza pizza;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "idorder")
	private Order order;

	/**
	 * Required for Java Persistence API.
	 */
	protected OrderPosition() {
	}

	/**
	 * Constructs an order position with the given order and pizza values.
	 *
	 * @param order the order of the order position
	 * @param pizza the pizza of the order position
	 */
	public OrderPosition(Order order, Pizza pizza) {
		this.order = order;
		this.pizza = pizza;
	}

	/**
	 * Returns the primary key of the order position.
	 *
	 * @return the primary key of the order position
	 */
	public long getId() {
		return id;
	}

	/**
	 * Returns the amount of pizza of the order position.
	 *
	 * @return the amount of pizza of the order position
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Returns the pizza of the order position.
	 *
	 * @return the pizza of the order position
	 */
	public Pizza getPizza() {
		return pizza;
	}

	/**
	 * Returns the order of the order position.
	 *
	 * @return the order of the order position
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * Increases the amount of pizza of the order position by one. The maximum
	 * value is 100.
	 */
	public void increaseAmount() {
		if (amount < MAX_AMOUNT) {
			amount += 1;
		}
	}

	/**
	 * Decreases the amount of pizza of the order position by one. The minimum
	 * value is 1.
	 */
	public void decreaseAmount() {
		if (amount > 1) {
			amount -= 1;
		}
	}

	/**
	 * Calculates the total amount of the pizzas in the order position.
	 *
	 * @return the total amount of the pizzas in the order position.
	 */
	public double calculateTotal() {
		return pizza.getPrice() * amount;
	}

	/**
	 * Returns all attribute values of the order position in human readable format.
	 */
	@Override
	public String toString() {
		return "OrderPosition(id=" + id + ", amount=" + amount + ", order=" + order
				+ ", pizza=" + pizza + ")";
	}
}
