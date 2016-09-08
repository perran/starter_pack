package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hasher {
	
	private PBEKeySpecFactory pbeKeySpecFactory;
	private SecretKeyFactory secretKeyFactory;
	
	public Hasher(PBEKeySpecFactory pbeKeySpecFactory) {
		this.pbeKeySpecFactory = pbeKeySpecFactory;
		
		try {
			this.secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();;
		}
	}
	
	public byte[] hash(String toHash, byte[] salt){
		int iterationCount = 100000;
		int keyLength = 128*8;
		PBEKeySpec spec = pbeKeySpecFactory.create(toHash.toCharArray(), salt, iterationCount , keyLength);
		try {
			return secretKeyFactory.generateSecret(spec).getEncoded();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean isEqual(byte[] digesta, byte[] digestb){
		return MessageDigest.isEqual(digesta, digestb);
	}
}
