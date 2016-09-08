package services;

import static java.lang.System.out;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import services.database.dto.DBLoginCredentials;
import services.login.LoginCredentials;
import services.login.LoginDbInteractor;
import services.login.LoginTicket;
import services.login.PasswordHelper;
import services.login.SaltAndHash;
import utils.PBEKeySpecFactory;
import utils.SecureRandomWrapper;
import utils.hashing.Hasher;
import utils.hashing.PBKDF2Hasher;

@Path("/login")
public class LoginService {
	
	private PasswordHelper passwordHelper;
	private LoginDbInteractor loginDbInteractor;

	public LoginService() {
		SecureRandomWrapper randomizer = new SecureRandomWrapper();
		PBEKeySpecFactory pbeKeySpecFactory = new PBEKeySpecFactory();
		Hasher hasher = new PBKDF2Hasher(pbeKeySpecFactory);
		passwordHelper = new PasswordHelper(randomizer, hasher);
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "UserPersistanceUnit" ); //do something about the UserPersistanceUnit name so it is dynamic?
		loginDbInteractor = new LoginDbInteractor(entityManagerFactory);
	}
	
	@GET
	@Path("/checkname/{name}")
	public boolean doesNameExists(@PathParam("name") String name){
		return loginDbInteractor.get(name) != null;
	}
	  
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean createAccount(LoginCredentials loginCredentials){
		out.println("Username: " + loginCredentials.username + ", Password: " + loginCredentials.password);
		SaltAndHash saltAndHash = passwordHelper.saltAndHash(loginCredentials.password);
		DBLoginCredentials dbCredentials = new DBLoginCredentials(loginCredentials.username,
				saltAndHash.getSalt(), saltAndHash.getHash());
		
		try{
			loginDbInteractor.add(dbCredentials);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public LoginTicket login(LoginCredentials loginCredentials){
		//SET TIME RESTRICTIONS WHEN THE CLIENT TRYING TO LOG IN TOO MANY TIMES
		out.println("Username: " + loginCredentials.username + ", Password: " + loginCredentials.password);
		
		DBLoginCredentials dbLoginCredentials = loginDbInteractor.get(loginCredentials.username);
		
		try{
			out.println("DB Username: " + dbLoginCredentials.getUsername() + ", DB Password: " + dbLoginCredentials.getPassword() + ", DB Salt: " +  dbLoginCredentials.getSalt());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		boolean match = false;
		
		if(dbLoginCredentials != null){
			match = passwordHelper.checkIfMatch(loginCredentials.password, dbLoginCredentials.getSalt(), 
					dbLoginCredentials.getPassword());
		}
		
		if(match){
			return new LoginTicket("valid_ticket_data", 60);
		} else {
			return new LoginTicket("INVALID", 0);
		}	
	}
}
