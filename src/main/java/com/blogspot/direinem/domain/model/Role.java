package com.blogspot.direinem.domain.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents an abstraction of a role. Encapsulates the attributes email address
 * and role name.
 *
 * @author Dirk Reinemann
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {

	private static final long serialVersionUID = -5597403457567960605L;

	public static final String ADMIN = "admin";
	public static final String USER = "user";
	public static final String EMPLOYEE = "employee";

	@Id
	private String email;
	private String name;

	/**
	 * Required for Java Persistence API.
	 */
	protected Role() {
	}

	/**
	 * Constructs a role with the given email address and role name.
	 *
	 * @param name the name of the role
	 * @param email the email address of the role
	 */
	public Role(String name, String email) {
		this.name = name;
		this.email = email;
	}

	/**
	 * Returns the email address of the role.
	 *
	 * @return the email address of the role
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns the name of the role.
	 *
	 * @return the name of the role
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns all attribute values of the order position in human readable format.
	 */
	@Override
	public String toString() {
		return "Role(email=" + email + ", name=" + name + ")";
	}
}
