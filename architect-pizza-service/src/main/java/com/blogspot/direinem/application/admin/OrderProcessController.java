package com.blogspot.direinem.application.admin;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.blogspot.direinem.domain.model.Order;
import com.blogspot.direinem.infrastructure.repository.OrderRepository;

/**
 * Represents the controller of the oder process view. Displays all orders and
 * changes their states.
 *
 * @author Dirk Reinemann
 */
@Named
@RequestScoped
public class OrderProcessController {

	private static final Logger LOG = Logger.getLogger(OrderProcessController.class);

	@Inject
	private OrderRepository orderRepository;

	private List<Order> orders = null;

	/**
	 * Loads all orders from the database, ordered by their date value.
	 */
	@PostConstruct
	public void init() {
		orders = orderRepository.getAllOrdersOrderedByNewest();
	}

	/**
	 * Returns the list of all orders.
	 *
	 * @return the list of orders
	 */
	public List<Order> getOrders() {
		return orders;
	}

	/**
	 * Changes the state of the order with the given id to the next possible value.
	 * Updates the order in the database.
	 *
	 * @param idOrder
	 */
	public void processOrder(int idOrder) {
		try {
			Order order = getOrder(idOrder);
			order.process();
			orderRepository.updateOrder(order);
		}
		catch (Exception e) {
			LOG.error("Exception", e);
		}
	}

	/**
	 * Searches the order with the given id in the local list of orders and
	 * returns it afterwards.
	 *
	 * @param idOrder the id of the order
	 * @return the order if it exists, null if not
	 */
	private Order getOrder(int idOrder) {
		Order result = null;
		for (Order order : orders) {
			if (order.getId() == idOrder) {
				result = order;
				break;
			}
		}
		return result;
	}
}
