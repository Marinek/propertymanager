package de.marinek.propertymanager.components.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SHA256Checksum {
	
	private  static Logger logger = LoggerFactory.getLogger(SHA256Checksum.class);

	public static String checksum(String... checksum) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(String.join("#", checksum).getBytes());
			String encryptedString = new String(messageDigest.digest());
			
			encryptedString = Base64.getEncoder().encodeToString(encryptedString.getBytes());
			
			return encryptedString;
		} catch (NoSuchAlgorithmException e) {
			logger.error("Error creating a checksum...", e);
		}
		
		return "";
	}
	
}
