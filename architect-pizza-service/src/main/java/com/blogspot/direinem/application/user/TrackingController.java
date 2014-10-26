package com.blogspot.direinem.application.user;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.blogspot.direinem.application.AbstractController;
import com.blogspot.direinem.domain.model.Order;
import com.blogspot.direinem.infrastructure.configuration.ConfigurationProperties;
import com.blogspot.direinem.infrastructure.repository.OrderRepository;

/**
 * Represents the controller of the order tracking view. Returns the orders of
 * the current user.
 *
 * @author Dirk Reinemann
 */
@Named
@RequestScoped
public class TrackingController extends AbstractController {

	@Inject
	private ConfigurationProperties configurationProperties;
	@Inject
	private OrderRepository orderRepository;

	private List<Order> orders;

	/**
	 * Loads the orders that belongs to the current user from the database.
	 */
	@PostConstruct
	public void init() {
		orders = orderRepository.getRecentOrdersByUser(getCurrentUsername(), configurationProperties.getRecentOrderCount());
	}

	/**
	 * Returns the list of orders from the current user.
	 *
	 * @return the list of orders
	 */
	public List<Order> getOrders() {
		return orders;
	}
}
