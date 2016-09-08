package services.login;

import java.util.Arrays;
import java.util.Base64;

import utils.SecureRandomWrapper;
import utils.hashing.Hasher;

public class PasswordHelper {

	private SecureRandomWrapper randomizer;
	private Hasher hasher;
	

	public PasswordHelper(SecureRandomWrapper randomizer, Hasher hasher) {
		super();
		this.randomizer = randomizer;
		this.hasher = hasher;
	}

	public SaltAndHash saltAndHash(String password){
		byte[] salt = randomizer.nextBytes(64);
		String encodedSalt = Base64.getEncoder().encodeToString(salt);
		byte[] hashedPassword = hasher.hash(password, salt);
		String encodedHashedPassword = Base64.getEncoder().encodeToString(hashedPassword);
		
		return new SaltAndHash(encodedSalt, encodedHashedPassword);
	}
	
	public boolean checkIfMatch(String password, String salt, String existingHash){
		byte[] decodedSalt = Base64.getDecoder().decode(salt);
		byte[] hash = hasher.hash(password, decodedSalt);
		byte[] decodedExistingHash = Base64.getDecoder().decode(existingHash);
		return Arrays.equals(hash, decodedExistingHash);
	}
}
