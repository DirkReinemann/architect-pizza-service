package com.blogspot.direinem.domain.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents an abstraction of an user. Encapsulates the attributes email address,
 * password, first name, last name, street, zip code, phone number and role.
 *
 * @author Dirk Reinemann
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

	/**
	 * Represents the roles of the users in the application.
	 *
	 * @author Dirk Reinemann
	 */
	public enum Role {
		ADMIN, EMPLOYEE, USER
	}

	private static final long serialVersionUID = 4054238001095588529L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String email;
	private String password;
	private String firstname;
	private String lastname;
	private String street;
	private String zip;
	private String phone;
	@Enumerated(EnumType.STRING)
	private Role role;

	/**
	 * Required for Java Persistence API.
	 */
	protected User() {
	}

	/**
	 * Constructs an user with the given values and the role USER. Does not
	 * encrypt the password of the user.
	 *
	 * @param email the email address of the user
	 * @param password the encrypted password of the user
	 * @param firstname the first name of the user
	 * @param lastname the last name of the user
	 * @param street the street of the user
	 * @param zip the zip code of the user
	 * @param phone the phone number of the user
	 */
	public User(String email, String password, String firstname,
			String lastname, String street, String zip, String phone) {

		this(Role.USER, email, password, firstname, lastname, street, zip, phone);
	}

	/**
	 * Constructs an administrator or employee with the given values. Does not
	 * encrypt the password of the user.
	 *
	 * @param role the role of the user
	 * @param email the login name of the user
	 * @param password the password of the user
	 */
	public User(Role role, String email, String password) {
		this(role, email, password, null, null, null, null, null);
	}

	/**
	 * Constructs an user with the given values and the given role. Does not
	 * encrypt the password of the user.
	 *
	 * @param role the role of the user
	 * @param email the email address of the user
	 * @param password the password of the user
	 * @param firstname the first name of the user
	 * @param lastname the last name of the user
	 * @param street the street of the user
	 * @param zip the zip code of the user
	 * @param phone the phone number of the user
	 */
	private User(Role role, String email, String password, String firstname,
			String lastname, String street, String zip, String phone) {

		this.role = role;
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.street = street;
		this.zip = zip;
		this.phone = phone;
	}

	/**
	 * Returns the primary key of the user.
	 *
	 * @return the primary key of the user
	 */
	public long getId() {
		return id;
	}

	/**
	 * Returns the email address of the user.
	 *
	 * @return the email address of the user
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns the password of the user.
	 *
	 * @return the password of the user
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of the user.
	 *
	 * @param password the password of the user
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns the first name of the user.
	 *
	 * @return the first name of the user
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Sets the first name of the user.
	 *
	 * @param firstname the first name of the user
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Returns the last name of the user.
	 *
	 * @return the last name of the user.
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Sets the last name of the user.
	 *
	 * @param lastname the last name of the user
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * Returns the street of the user.
	 *
	 * @return the street of the user
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Sets the street of the user.
	 *
	 * @param street the street of the user
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Returns the zip code of the user.
	 *
	 * @return the zip code of the user
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * Sets the zip code of the user.
	 *
	 * @param zip the zip code of the user
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * Returns the phone number of the user.
	 *
	 * @return the phone number of the user
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the phone number of the user.
	 *
	 * @param phone the phone number of the user
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Returns the role of the user.
	 *
	 * @return the role of the user
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Checks whether the user has the given role. Returns true on success or
	 * false on failure.
	 *
	 * @param role the role to check
	 * @return true if the user has the role, false if not
	 */
	public boolean isInRole(Role role) {
		return (this.role == role) ? true : false;
	}

	/**
	 * Returns all attribute values of the order position in human readable format.
	 */
	@Override
	public String toString() {
		return "User(id=" + id + ", email=" + email + ", password=" + password
				+ ", firstname=" + firstname + ", lastname=" + lastname + ", "
				+ street + ", zip=" + zip + ", phone=" + phone + ")";
	}
}
