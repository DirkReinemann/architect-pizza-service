package com.blogspot.direinem.infrastructure.encryption;

/**
 * Provides an interface for encryption.
 * 
 * @author Dirk Reinemann
 */
public interface Encrpytion {
	
	/**
	 * Encrypts the given value.
	 * 
	 * @param value the value that needs to be encrypted
	 * @return the encrypted value
	 */
	String encrypt(String value);
}
