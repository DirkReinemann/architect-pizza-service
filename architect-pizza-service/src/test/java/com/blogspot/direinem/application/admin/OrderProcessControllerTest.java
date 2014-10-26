package com.blogspot.direinem.application.admin;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import org.apache.openejb.jee.Beans;
import org.apache.openejb.jee.EjbJar;
import org.apache.openejb.jee.StatelessBean;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.junit.Module;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.blogspot.direinem.AbstractTest;
import com.blogspot.direinem.domain.model.Category;
import com.blogspot.direinem.domain.model.Ingredient;
import com.blogspot.direinem.domain.model.Newsletter;
import com.blogspot.direinem.domain.model.Order;
import com.blogspot.direinem.domain.model.OrderPosition;
import com.blogspot.direinem.domain.model.Pizza;
import com.blogspot.direinem.domain.model.User;
import com.blogspot.direinem.infrastructure.configuration.ConfigurationProperties;
import com.blogspot.direinem.infrastructure.repository.OrderRepository;

/**
 * Represents the test class of the @OrderProcessController.
 *
 * @author Dirk Reinemann
 */
@RunWith(ApplicationComposer.class)
public final class OrderProcessControllerTest extends AbstractTest {

	@Inject
	private OrderProcessController orderProcessController;
	@Inject
	private OrderRepository orderRepository;

	/**
	 * Configures the OpenWebBean-Module.
	 *
	 * @return the CDI beans
	 */
	@Module
	public Beans beans() {
		Beans beans = new Beans();
		beans.addManagedClass(ConfigurationProperties.class);
		beans.addManagedClass(OrderProcessController.class);
		return beans;
	}

	/**
	 * Configures the OpenEJB-Module.
	 *
	 * @return the EJB-jar
	 */
	@Module
	public EjbJar ejbs() {
		EjbJar ejbJar = new EjbJar();
		ejbJar.addEnterpriseBean(new StatelessBean(OrderRepository.class));
		return ejbJar;
	}

	@Override
	public Properties getAdditionalProperties() {
		return null;
	}

	@Override
	protected List<Class<?>> getPersistenceClasses() {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		classes.add(Order.class);
		classes.add(Category.class);
		classes.add(Ingredient.class);
		classes.add(OrderPosition.class);
		classes.add(Pizza.class);
		classes.add(User.class);
		classes.add(Newsletter.class);
		return classes;
	}

	/**
	 * Executes the SQL-Script to produce the necessary database state. Processes
	 * the order and checks whether the state changed from ordered to prepared.
	 *
	 * @throws Exception exception while testing
	 */
	@Test
	public final void processOrderAndChangeFromOrderedToPrepared() throws Exception {
		executeSql("processOrderAndChangeFromOrderedToPrepared.sql");
		int idOrder = 3;

		orderProcessController.processOrder(idOrder);
		assertEquals(Order.OrderState.PREPARED, orderRepository.getOrder(idOrder).getOrderState());
	}
}
