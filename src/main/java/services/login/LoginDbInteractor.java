package services.login;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import services.database.dto.DBLoginCredentials;

public class LoginDbInteractor {

	private EntityManagerFactory entityManagerFactory;
	
	public LoginDbInteractor(EntityManagerFactory entityManagerFactory) {
		super();
		this.entityManagerFactory = entityManagerFactory;
	}

	private EntityManager pre(){
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		return entityManager;
	}
	
	private void post(EntityManager entityManager){
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	public void add(DBLoginCredentials credentials) throws Exception{
		EntityManager entityManager = pre();
		
		String username = credentials.getUsername();
		DBLoginCredentials existing = getInternal(entityManager, username);
		
		if(existing != null){
			post(entityManager);
			throw new Exception("There is already a user with the name " + username);
		}
		
		entityManager.persist(credentials);
		post(entityManager);
	}
	
	public DBLoginCredentials get(String username){
		EntityManager entityManager = pre();
		DBLoginCredentials lc = getInternal(entityManager, username);
		post(entityManager);
		
		return lc;
	}
	
	private DBLoginCredentials getInternal(EntityManager entityManager, String username){
		String queryString = "select n from DBLoginCredentials n where username = '" + username + "'";
		Query query = entityManager.createQuery(queryString, DBLoginCredentials.class);
		List<DBLoginCredentials> resultList = query.getResultList();
		
		if(resultList.isEmpty()){
			return null;
		}
		
		return resultList.get(0);
	}
	
}
