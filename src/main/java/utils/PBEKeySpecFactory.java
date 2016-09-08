package utils;

import javax.crypto.spec.PBEKeySpec;

public class PBEKeySpecFactory {
	public PBEKeySpec create(char[] password, byte[] salt, int iterationCount, int keyLength){
		return new PBEKeySpec(password, salt, iterationCount, keyLength);
	}
}
