package com.blogspot.direinem.application.user;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.blogspot.direinem.application.AbstractController;
import com.blogspot.direinem.domain.model.Category;
import com.blogspot.direinem.domain.model.Ingredient;
import com.blogspot.direinem.domain.model.Order;
import com.blogspot.direinem.domain.model.Order.Payment;
import com.blogspot.direinem.domain.model.Pizza;
import com.blogspot.direinem.domain.model.User;
import com.blogspot.direinem.domain.service.CartService;
import com.blogspot.direinem.infrastructure.configuration.ConfigurationProperties;
import com.blogspot.direinem.infrastructure.repository.OrderRepository;
import com.blogspot.direinem.infrastructure.repository.UserRepository;

/**
 * Represents the controller of the order view. Creates and saves an order.
 *
 * @author Dirk Reinemann
 */
@Named
@RequestScoped
public class OrderController extends AbstractController {

	private static final Logger LOG = Logger.getLogger(OrderController.class);

	public static final String ORDER_STATE_FAILED = "ORDER_STATE_FAILED";
	public static final String ORDER_STATE_NO_GOODS = "ORDER_STATE_NO_GOODS";

	@Inject
	private ConfigurationProperties configurationProperties;
	@Inject
	private OrderRepository orderRepository;
	@Inject
	private UserRepository userRepository;
	@Inject
	private CartService cartService;

	private String state;

	/**
	 * Returns the payment values - cash and ec-card.
	 *
	 * @return the payment values
	 */
    public Payment[] getPayments() {
    	return Payment.values();
    }

    /**
     * Returns the list of all categories.
     *
     * @return the list of categories
     */
    public List<Category> getCategories() {
    	return cartService.getCategories();
    }

    public List<Ingredient> getIngredients() {
    	return cartService.getIngredients();
    }

    /**
     * Returns the current pizza.
     *
     * @return the current pizza
     */
    public Pizza getPizza() {
        return cartService.getPizza();
    }

    /**
     * Returns the current order.
     *
     * @return the current oder
     */
    public Order getOrder() {
		return cartService.getOrder();
	}

    /**
     * Checks whether the current pizza has any ingredients and adds it to the
     * current order of the cart on success. Creates an empty pizza object
     * afterwards.
     */
    public void addPizza() {
    	if (cartService.getPizza().hasIngredients()) {
    		cartService.getOrder().addPizza(cartService.getPizza());
        	cartService.newPizza();
    	}
    }

    /**
     * Sets the value of the current pizza object to the given pizza. Uses the
     * pizza as template and navigates to the configurator view.
     *
     * @param pizza the pizza to user
     * @return the name of the configurator view
     */
    public String editPizza(Pizza pizza) {
    	cartService.setPizza(pizza);
    	return "configurator.xhtml";
    }

    /**
     * Returns the state of the process.
     *
     * @return the state of the process
     */
	public String getState() {
		return state;
	}

	/**
	 * Loads the current user object from the database and finishes the order
	 * with it. Saves the order to the database and creates a new and empty
	 * order object. Returns the order successful view on success or sets the
	 * state of the process to an error message.
	 *
	 * @return the order successful view on success or null on failure
	 */
    public String createOrder() {
    	String value = null;

    	if (cartService.getOrder().hasPizzas()) {
    		try {
    			User user = userRepository.getUserByEmail(getCurrentUsername());
    			cartService.getOrder().finish(user);
    			orderRepository.saveOrder(cartService.getOrder());
    			cartService.newOrder();
    			value = "success.xhtml";
    		}
    		catch (Exception e) {
    			LOG.error("Exception", e);
    			this.state = configurationProperties.getStateMessage(ORDER_STATE_FAILED);
    		}
    	}
    	else {
    		this.state = configurationProperties.getStateMessage(ORDER_STATE_NO_GOODS);
    	}
    	return value;
    }
}
