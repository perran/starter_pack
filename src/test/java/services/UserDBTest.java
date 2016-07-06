package services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import services.database.dto.DBLoginCredentials;

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

    public <T> void clearDb(Class<T> clazz){
    	EntityManager entityManager = entityManagerFactory.createEntityManager();
    	entityManager.getTransaction().begin();

    	Query query = entityManager.createQuery( "SELECT h FROM DBLoginCredentials h" , clazz);

    	List<T> resultList = query.getResultList();
		
		for (T lc : resultList) {
			entityManager.remove(lc);
		}
		
		entityManager.getTransaction().commit();
		entityManager.close();
    }
    
	@Test
	public void testUserDB2(){
		clearDb(DBLoginCredentials.class);
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
		String salt = "salt";
		String password = "password";
		
		String username = "Perka";
		
		DBLoginCredentials dbLoginCredentials = new DBLoginCredentials(username, salt, password);
		
		entityManager.persist(dbLoginCredentials);
		
		entityManager.getTransaction().commit();
		
		entityManager.close();
		
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		//now query by the name
		
		Query query = entityManager.createQuery("select n from DBLoginCredentials n where username = '" + username + "'");
		List<DBLoginCredentials> resultList = query.getResultList();
		
		
		assertThat(resultList.size(), equalTo(1));
		
		DBLoginCredentials actual = resultList.get(0);
		assertThat(actual.getUsername(), equalTo(username));
		
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
}
