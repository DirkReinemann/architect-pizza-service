package com.blogspot.direinem.infrastructure.encryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.apache.log4j.Logger;

/**
 * Represents an implementiation of the @Encryption interface that provides
 * a SHA-256 encryption.
 *
 * @author Dirk Reinemann
 */
@Named
@RequestScoped
public class ShaEncryptor implements Encrpytion {

	private static final Logger LOG = Logger.getLogger(ShaEncryptor.class);

	private static final String SHA_ENCPRYTION = "SHA-256";

	/**
	 * @see Encrpytion#encrypt(String)
	 *
	 * @return {@link Encrpytion#encrypt(String)}
	 */
	@Override
	public String encrypt(String value) {
		MessageDigest messageDigest;

		String result = null;
		try {
			messageDigest = MessageDigest.getInstance(SHA_ENCPRYTION);
			messageDigest.update(value.getBytes("UTF-8"));
			byte[] digest = messageDigest.digest();

			StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < digest.length; i++) {
	        	sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
	        }
			result = sb.toString();
		}
		catch (NoSuchAlgorithmException e) {
			LOG.error("NoSuchAlgorithmException", e);
		}
		catch (UnsupportedEncodingException e) {
			LOG.error("UnsupportedEncodingException", e);
		}
		return result;
	}

}
