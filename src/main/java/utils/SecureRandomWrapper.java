package utils;

import java.security.SecureRandom;

public class SecureRandomWrapper {
	SecureRandom random;

	public SecureRandomWrapper() {
		super();
		this.random = new SecureRandom();
	}
	
	public byte[] nextBytes(int numberOfBytes){
		byte[] bytes = new byte[numberOfBytes];
		random.nextBytes(bytes);
		return bytes;
	}
	
	
}
