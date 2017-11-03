package com.blogspot.direinem.infrastructure.encryption;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Represents the test class of the @ShaEncryptor.
 *
 * @author Dirk Reinemann
 */
public final class ShaEcnryptorTest {

	private static ShaEncryptor shaEncryptor;

	/**
	 * Creates an object of @ShaEncryptor.
	 */
	@BeforeClass
	public static void beforeClass() {
		shaEncryptor = new ShaEncryptor();
	}

	/**
	 * Encrypts the string admin and cheks the value.
	 */
	@Test
	public void encryptAdmin() {
		String encryption = shaEncryptor.encrypt("admin");
		assertEquals("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918", encryption);
	}
}
