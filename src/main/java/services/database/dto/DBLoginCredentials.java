package services.database.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;

@Entity
public class DBLoginCredentials {
	
	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Field(analyze=Analyze.NO)
	private String username;
	
	private String salt;
	
    private String password;
    
	public DBLoginCredentials() {
		super();
	}

	public DBLoginCredentials(String username, String salt, String password) {
		super();
		this.username = username;
		this.salt = salt;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getSalt() {
		return salt;
	}

	public String getPassword() {
		return password;
	}
	
	
}
