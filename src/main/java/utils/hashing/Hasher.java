package utils.hashing;

public interface Hasher {

	byte[] hash(String toHash, byte[] salt);

}