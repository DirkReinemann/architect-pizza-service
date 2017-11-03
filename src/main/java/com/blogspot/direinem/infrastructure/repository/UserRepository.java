package com.blogspot.direinem.infrastructure.repository;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.blogspot.direinem.domain.model.User;

/**
 * Represents a repository to access and manipulate the user objects.
 *
 * @author Dirk Reinemann
 */
@Stateless
public class UserRepository extends AbstractRepository {

	/**
	 * Loads the user with the given email address form the database.
	 *
	 * @param email the email address of the user
	 * @return the user object
	 */
	public User getUserByEmail(String email) {
		Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.email=:email");
		query.setParameter("email", email);
		return (User) query.getSingleResult();
	}

	/**
	 * Checks whether the user with the given email address already exists in the
	 * database or not.
	 *
	 * @param email the email address of the user
	 * @return true if the user exists, false if not
	 */
	public boolean existsUser(String email) {
		Query query = entityManager.createQuery("SELECT COUNT(u) FROM User u WHERE u.email=:email");
		query.setParameter("email", email);
		Long count = (Long) query.getSingleResult();
		return (count == 0) ? false : true;
	}

	/**
	 * Saves the given user object in the database.
	 *
	 * @param user the user object to save
	 */
	public void saveUser(User user) {
		entityManager.persist(user);
	}

	/**
	 * Deletes the user with the given primary key from the database.
	 *
	 * @param id the primary key of the user
	 */
	public void deleteUser(long id) {
		User user = entityManager.find(User.class, id);
		entityManager.remove(user);
	}

	/**
	 * Loads the number of users that have the role administrator from the
	 * database.
	 *
	 * @return the number of administrators
	 */
	public long getAdminCount() {
		Query query = entityManager.createQuery("SELECT COUNT(u) FROM User u WHERE u.role=:name");
		query.setParameter("name", User.Role.ADMIN);
		return (Long) query.getSingleResult();
	}

	/**
	 * Loads the users that have the role administrator or employee from the database.
	 *
	 * @return the list of employees and administrators
	 */
	@SuppressWarnings("unchecked")
	public List<User> getAdminsAndEmployees() {
		Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.role=:admin OR u.role=:employee");
		query.setParameter("admin", User.Role.ADMIN);
		query.setParameter("employee", User.Role.EMPLOYEE);
		return query.getResultList();
	}

	/**
	 * Loads the users that have the role employee from the database.
	 *
	 * @return the list of employees
	 */
	@SuppressWarnings("unchecked")
	public List<User> getEmployees() {
		Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.role=:employee");
		query.setParameter("employee", User.Role.EMPLOYEE);
		return query.getResultList();
	}

	/**
	 * Updates the state of the given user in the database.
     *
     * @param user the user to update
     */
	public void updateUser(User user) {
		entityManager.merge(user);
	}
}