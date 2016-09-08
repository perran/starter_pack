package utils.hashing;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import utils.PBEKeySpecFactory;

public class PBKDF2Hasher implements Hasher {
	
	private PBEKeySpecFactory pbeKeySpecFactory;
	private SecretKeyFactory secretKeyFactory;
	
	public PBKDF2Hasher(PBEKeySpecFactory pbeKeySpecFactory) {
		this.pbeKeySpecFactory = pbeKeySpecFactory;
		
		try {
			this.secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();;
		}
	}
	
	@Override
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
}
