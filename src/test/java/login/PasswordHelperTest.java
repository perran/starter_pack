package login;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Base64;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import services.login.PasswordHelper;
import services.login.SaltAndHash;
import utils.PBEKeySpecFactory;
import utils.SecureRandomWrapper;
import utils.hashing.Hasher;
import utils.hashing.PBKDF2Hasher;

@RunWith(MockitoJUnitRunner.class)
public class PasswordHelperTest {

	@Mock
	private SecureRandomWrapper secureRandomWrapper;
	
	@Test
	public void itShouldBeAbleToMatchPasswordHashesForSameSaltedPasswords(){
		byte[] expectedSalt = "salt".getBytes();
		
		Mockito.when(secureRandomWrapper.nextBytes(64)).thenReturn(expectedSalt);
		PBEKeySpecFactory pbeKeySpecFactory = new PBEKeySpecFactory();
		Hasher hasher = new PBKDF2Hasher(pbeKeySpecFactory);
		PasswordHelper passwordHelper = new PasswordHelper(secureRandomWrapper, hasher);
		SaltAndHash saltAndHash = passwordHelper.saltAndHash("password");
		
		String actualSalt = saltAndHash.getSalt();
		
		String encodedExpectedSalt = Base64.getEncoder().encodeToString(expectedSalt);

		String hash = saltAndHash.getHash();
		
		assertThat(actualSalt, equalTo(encodedExpectedSalt));
		
		boolean match = passwordHelper.checkIfMatch("password", actualSalt, hash);
		
		assertTrue(match);
	}
}
