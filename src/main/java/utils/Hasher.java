package utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

	private MessageDigest digest;
	
	public Hasher() {
		try {
			this.digest = MessageDigest.getInstance("SHA-256");
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();;
		}
	}
	
	public byte[] hash(String toHash){
		try {
			return digest.digest(toHash.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean isEqual(byte[] digesta, byte[] digestb){
		return MessageDigest.isEqual(digesta, digestb);
	}
}
