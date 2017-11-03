package com.blogspot.direinem.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Represents an abstraction of an order. Encapsulates the attributes order
 * state, order time, delivery time, payment, addition, user, pizza count and
 * order positions. Provides functions for finishing an order, remove and add
 * order positions, process an order and get the total price of an order.
 *
 * @author Dirk Reinemann
 */
@Entity
@Table(name = "orders")
public class Order implements Serializable {

	private static final long serialVersionUID = -5546922707175319001L;

	/**
	 * Represents the states of an order.
	 *
	 * @author Dirk Reinemann
	 */
	public enum OrderState {
		ORDERED, PREPARED, DELIVERED
	}

	/**
	 * Represents the payment type of an order.
	 *
	 * @author Dirk Reinemann
	 */
	public enum Payment {
        CASH("Cash"), EC_CARD("EC Card");

        private String label;

        /**
         * Constructs a payment with the given label.
         *
         * @param label the label of the payment.
         */
        private Payment(String label) {
        	this.label = label;
        }

        /**
         * Returns the label of the payment.
         *
         * @return the label of the payment
         */
        public String getLabel() {
			return label;
		}
    }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Enumerated(EnumType.STRING)
	private OrderState orderState = OrderState.ORDERED;
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderTime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date deliveryTime;
	@Enumerated(EnumType.STRING)
	private Payment payment = Payment.CASH;
    private String addition;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "iduser")
	private User user;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderPosition> orderPositions = new ArrayList<OrderPosition>();
	@Transient
	private int pizzaCount;

	/**
	 * Returns the primary key of the order.
	 *
	 * @return the primary key of the order
	 */
	public long getId() {
		return id;
	}

	/**
	 * Returns the state of the order.
	 *
	 * @return the state of the order.
	 */
	public OrderState getOrderState() {
		return orderState;
	}

	/**
	 * Returns the order time of the order.
	 *
	 * @return the order time of the order
	 */
	public Date getOrderTime() {
		return orderTime;
	}

	/**
	 * Returns the delivery time of the order.
	 *
	 * @return the delivery time of the order
	 */
	public Date getDeliveryTime() {
		return deliveryTime;
	}

	/**
	 * Sets the delivery time of the order to the actual time.
	 */
	public void setDelivered() {
		deliveryTime = new Date();
	}

	/**
	 * Returns the payment type of the order.
	 *
	 * @return the payment type of the order
	 */
	public Payment getPayment() {
        return payment;
    }

	/**
	 * Sets the payment type of the order.
	 *
	 * @param payment the payment type of the order
	 */
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    /**
     * Returns the order additions of the order.
     *
     * @return the order additions of the order
     */
    public String getAddition() {
        return addition;
    }

    /**
     * Sets the addition of the order. Checks the length of the given value
     * after trimming and that it is nor null.
     *
     * @param addition the addition of the order.
     */
    public void setAddition(String addition) {
    	if (addition != null && addition.trim().length() > 0) {
    		this.addition = addition;
    	}
    }

    /**
     * Returns the user of the order.
     *
     * @return the user of the order.
     */
    public User getUser() {
		return user;
	}

    /**
     * Returns the list of order positions of the order.
     *
     * @return the list of order positions of the order
     */
	public List<OrderPosition> getOrderPositions() {
		return orderPositions;
	}

	/**
	 * Removes an order position from the order by the given index in the local
	 * list of order positions.
	 *
	 * @param index the index of the order position to remove
	 */
	public void removeOrderPosition(int index) {
		orderPositions.remove(index);
	}

	/**
	 * Sets the pizza count to the given value.
	 *
	 * @param pizzaCount the pizza count to set
	 */
	public void setPizzaCount(int pizzaCount) {
		this.pizzaCount = pizzaCount;
	}

	/**
	 * Adds a pizza to the order. Checks if the given pizza already exists in
	 * this order. Creates an order position with the given pizza if not or
	 * otherwise increases the amount of pizza.
	 *
	 * @param pizza the pizza to add
	 */
	public void addPizza(Pizza pizza) {
		int index = getOrderPositionWithPizza(pizza.getId());
		if (index == -1) {
			OrderPosition orderPosition = new OrderPosition(this, pizza);
			orderPositions.add(orderPosition);
		}
		else {
			orderPositions.get(index).increaseAmount();
		}
	}

	/**
	 * Decreases the amount of pizza by one with the given index of the order
	 * position in the local list of order positions.
	 *
	 * @param orderPositionIndex the index of the order position
	 */
	public void decreasePizzaAmount(int orderPositionIndex) {
		orderPositions.get(orderPositionIndex).decreaseAmount();
	}

	/**
	 * Increases the amount of pizza by one with the given index of the order
	 * position in the local list of order positions.
	 *
	 * @param orderPositionIndex the index of the order position
	 */
	public void increasePizzaAmount(int orderPositionIndex) {
		orderPositions.get(orderPositionIndex).increaseAmount();
	}

	/**
	 * Checks if the order contains any pizzas.
	 *
	 * @return true if there are any pizzas in the order, false if not
	 */
	public boolean hasPizzas() {
		boolean result = false;
		if (orderPositions != null && orderPositions.size() > 0) {
			result = true;
		}
		return result;
	}

	/**
	 * Returns the total amount of all positions in the order.
	 *
	 * @return the total amount of all positions in the order
	 */
	public double getTotal() {
		double total = 0;
		for (OrderPosition orderPosition : orderPositions) {
			total += orderPosition.calculateTotal();
		}
		return total;
	}

	/**
	 * Sets the user of the order with the given value and the order time to
	 * to the actual time.
	 *
	 * @param user the user of the order
	 */
	public void finish(User user) {
		this.user = user;
		this.orderTime = new Date();
	}

	/**
	 * Searches for an order position that contains a pizza with the given
	 * identifier and returns the index in the local list of order positions.
	 *
	 * @param pizzaId the identifier of the pizza
	 * @return the index of the order position if the pizza exists, -1 if not
	 */
	private int getOrderPositionWithPizza(long pizzaId) {
		int index = -1;
		if (pizzaId != 0) {
			for (int i = 0; i < orderPositions.size() && index == -1; i++) {
				if (orderPositions.get(i).getPizza().getId() == pizzaId) {
					index = i;
				}
			}
		}
		return index;
	}

	/**
	 * Changes the state of the current order to the next possible value. From
	 * ordered to prepared and from prepared to delivered.
	 */
	public void process() {
		switch (orderState) {
		case ORDERED:
			this.orderState = OrderState.PREPARED;
			break;
		case PREPARED:
			this.orderState = OrderState.DELIVERED;
			this.deliveryTime = new Date();
			break;
		case DELIVERED:
			break;
		}
	}

	/**
	 * Returns all attribute values of the order in human readable format.
     */
	@Override
	public String toString() {
		return "Order(id=" + id + ", orderState=" + orderState + ", ordered="
				+ orderTime + ", delivered=" + deliveryTime + ", user="
				+ user + ", payment=" + payment + ", addition=" + addition + ")";
	}
}
