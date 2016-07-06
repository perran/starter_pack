package services.login;

public class SaltAndHash {
	private String salt;
	private String hash;
	
	public SaltAndHash(String salt, String hash) {
		super();
		this.salt = salt;
		this.hash = hash;
	}

	public String getSalt() {
		return salt;
	}

	public String getHash() {
		return hash;
	}
}
