package services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import services.database.dto.DBUser;

public class UserDBTest {

	private static EntityManagerFactory entityManagerFactory;
	
	@BeforeClass
    public static void setUpEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory( "UserPersistanceUnit" );
    }

    @AfterClass
    public static void closeEntityManagerFactory() {
        entityManagerFactory.close();
    }
	
	@Test
	public void testUserDB(){
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
			
		DBUser dbUser = new DBUser("Data", "Pata");
		
		entityManager.persist(dbUser);
		
		entityManager.getTransaction().commit();
		
		entityManager.close();
		
		//now read from db
		
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		DBUser find = entityManager.find( DBUser.class, dbUser.getId() );
		
		Assert.assertThat(find.getFirstName(), org.hamcrest.CoreMatchers.is("Data"));
		
		entityManager.close();
	}
	
}
