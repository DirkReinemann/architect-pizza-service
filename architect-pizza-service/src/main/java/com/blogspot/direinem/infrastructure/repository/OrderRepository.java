package com.blogspot.direinem.infrastructure.repository;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.blogspot.direinem.domain.model.Order;
import com.blogspot.direinem.domain.model.OrderPosition;
import com.blogspot.direinem.domain.model.Pizza;
import com.blogspot.direinem.domain.model.User;

/**
 * Represents a repository to access and manipulate the order objects and all
 * objects in the hierarchy of it.
 *
 * @author Dirk Reinemann
 */
@Stateless
public class OrderRepository extends AbstractRepository {

	private static final Logger LOG = Logger.getLogger(OrderRepository.class);

	/**
	 * Loads all orders that belongs to the given email address of the user.
	 *
	 * @param email the email address of the user
	 * @return the list of orders
	 */
    @SuppressWarnings("unchecked")
	public Order findOrderByEmail(String email) {
        Query query =  entityManager.createQuery("SELECT o FROM Order o WHERE o.user.email=:email ORDER BY o.orderTime DESC", Order.class);
        query.setParameter("email", email);
        List<Order> orders = query.getResultList();
        return orders.isEmpty() ? null : orders.get(0);
    }

    /**
     * Saves the given order to the database. Iterates all order positions and
     * checks whether the pizza already exists in the database. Replaces it if
     * true.
     *
     * @param order the order to save
     * @throws Exception an exception while saving the order
     */
    public void saveOrder(Order order) throws Exception {
    	try {
    		for (OrderPosition orderPosition : order.getOrderPositions()) {
    			orderPosition.getPizza().setId(findExistingPizzaId(orderPosition.getPizza()));
    		}
    		entityManager.merge(order);
    	}
    	catch (Exception e) {
    		LOG.error("saveOrder(): Exception", e);
    		throw e;
    	}
    }

    /**
     * Queries the database for a pizza with identical ingredients like the
     * given pizza to prevent unnecessary memory usage.
     *
     * @param pizza the pizza to check
     * @return the primary key of the pizza with identical ingredients
     */
    private long findExistingPizzaId(Pizza pizza) {
    	long result = 0;
    	try {
    		List<Long> ids = pizza.getIngredientIds();
    		Query query = entityManager.createQuery("SELECT p.id FROM Pizza p INNER JOIN p.ingredients i WHERE i.id IN :ids GROUP BY p.id HAVING COUNT(p.id)=:count");
    		query.setParameter("ids", ids);
    		query.setParameter("count", ids.size());
    		result = (Long) query.getSingleResult();
    	}
    	catch (NoResultException e) {
    		LOG.info("findExistingPizzaId(): No result found for " + pizza);
		}
    	return result;
    }

    /**
     * Queries the database for a user.
     *
     * @param user the user to search for
     * @return the primary key of the user or 0
     */
    public long findExsitingUserId(User user) {
    	int result = 0;
    	try {
    		Query query = entityManager.createQuery("SELECT u.id FROM User u WHERE u.firstname=:firstname AND u.lastname=:lastname AND u.street=:street AND u.zip=:zip AND u.phone=:phone");
    		query.setParameter("firstname", user.getFirstname());
    		query.setParameter("lastname", user.getLastname());
    		query.setParameter("street", user.getStreet());
    		query.setParameter("zip", user.getZip());
    		query.setParameter("phone", user.getPhone());
    		result = (Integer) query.getSingleResult();
    	}
    	catch (NoResultException e) {
    		LOG.info("findExsitingUserId(): No result found for " + user);
		}
    	return result;
    }

    /**
     * Loads all orders from the database and the count of order positions.
     * Sets the count of order positions to the order.
     *
     * @return the list of orders
     */
    @SuppressWarnings("unchecked")
	public List<Order> getAllOrdersAndPizzaCount() {
    	List<Object[]> tuples = entityManager.createQuery("SELECT o, COUNT(o.orderPositions) FROM Order o").getResultList();

    	List<Order> orders = new LinkedList<Order>();
    	for (Object[] tuple : tuples) {
    		Order order = (Order) tuple[0];
    		Long pizzaCount = (Long) tuple[1];
    		order.setPizzaCount(pizzaCount.intValue());
    		orders.add(order);
    	}
    	return orders;
    }

    /**
     * Loads all orders from the database ordered by the order date.
     *
     * @return the list of orders
     */
    @SuppressWarnings("unchecked")
	public List<Order> getAllOrdersOrderedByNewest() {
    	return entityManager.createQuery("SELECT o FROM Order o ORDER BY o.orderTime ASC").getResultList();
    }

    /**
     * Loads the order with the given primary key from the database.
     *
     * @param idOrder the primary key of the order
     * @return the order or null if it was not found
     */
    public Order getOrder(long idOrder) {
    	return entityManager.find(Order.class, idOrder);
    }

    /**
     * Updates the given order in the database.
     *
     * @param order the order to update
     */
    public void updateOrder(Order order) {
    	entityManager.merge(order);
    }

    /**
     * Loads the given number of orders from the user with the given email
     * address from the database ordered by the delivery time.
     *
     * @param email the email address of the user
     * @param count the number of orders to load
     * @return the list of orders
     */
    @SuppressWarnings("unchecked")
	public List<Order> getRecentOrdersByUser(String email, int count) {
    	Query query = entityManager.createQuery("SELECT o FROM Order o WHERE o.user.email=:email ORDER BY o.deliveryTime ASC");
    	query.setMaxResults(count);
    	query.setParameter("email", email);
    	return query.getResultList();
    }
}