package com.blogspot.direinem.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class UserTest {

	@Test
	public void createUserAndChangeAttributes() {
		User user = new User("email", "password", "firstname", "lastname", "street", "zip", "phone");
		user.setPassword("newPassword");
		user.setFirstname("newFirstname");
		user.setLastname("newLastname");
		user.setStreet("newStreet");
		user.setZip("newZip");
		user.setPhone("newPhone");

		assertEquals("newPassword", user.getPassword());
		assertEquals("newFirstname", user.getFirstname());
		assertEquals("newLastname", user.getLastname());
		assertEquals("newStreet", user.getStreet());
		assertEquals("newZip", user.getZip());
		assertEquals("newPhone", user.getPhone());
		assertEquals(User.Role.USER, user.getRole());
	}

	@Test
	public void createUserWithRoleUserAndCheckAttributesAndRole() {
		User user = new User("email", "password", "firstname", "lastname", "street", "zip", "phone");
		assertEquals(0, user.getId());
		assertEquals("email", user.getEmail());
		assertEquals("password", user.getPassword());
		assertEquals("firstname", user.getFirstname());
		assertEquals("lastname", user.getLastname());
		assertEquals("street", user.getStreet());
		assertEquals("zip", user.getZip());
		assertEquals("phone", user.getPhone());
		assertTrue(user.isInRole(User.Role.USER));
	}

	@Test
	public void createUserWithRoleEmployeeAndCheckAttributesAndRole() {
		User user = new User(User.Role.EMPLOYEE, "email", "password");
		assertEquals(0, user.getId());
		assertEquals("email", user.getEmail());
		assertEquals("password", user.getPassword());
		assertNull(user.getFirstname());
		assertNull(user.getLastname());
		assertNull(user.getStreet());
		assertNull(user.getZip());
		assertNull(user.getPhone());
		assertTrue(user.isInRole(User.Role.EMPLOYEE));
	}

	@Test
	public void createUserWithRoleAdminAndCheckAttributesAndRole() {
		User user = new User(User.Role.ADMIN, "email", "password");
		assertEquals(0, user.getId());
		assertEquals("email", user.getEmail());
		assertEquals("password", user.getPassword());
		assertNull(user.getFirstname());
		assertNull(user.getLastname());
		assertNull(user.getStreet());
		assertNull(user.getZip());
		assertNull(user.getPhone());
		assertTrue(user.isInRole(User.Role.ADMIN));
	}
}
