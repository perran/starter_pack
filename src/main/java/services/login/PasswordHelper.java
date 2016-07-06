package services.login;

import java.util.Base64;

import utils.Hasher;
import utils.SecureRandomWrapper;

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
		String saltedPassword = encodedSalt + password;
		byte[] hashedPassword = hasher.hash(saltedPassword);
		String encodedHashedPassword = Base64.getEncoder().encodeToString(hashedPassword);
		
		return new SaltAndHash(encodedSalt, encodedHashedPassword);
	}
	
	public boolean checkIfMatch(String password, String salt, String existingHash){
		byte[] hash = hasher.hash(salt + password);
		byte[] decodedExistingHash = Base64.getDecoder().decode(existingHash);
		return hasher.isEqual(hash, decodedExistingHash);
	}
}
