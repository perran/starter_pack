package services.login;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import services.database.dto.DBLoginCredentials;
import utils.DbTransactionHelper;

public class LoginDbInteractor {

	private DbTransactionHelper dbTransactionHelper;

	public LoginDbInteractor(DbTransactionHelper dbTransactionHelper) {
		super();
		this.dbTransactionHelper = dbTransactionHelper;
	}
		
	public void add(DBLoginCredentials credentials) throws Exception{
		EntityManager entityManager = dbTransactionHelper.beginTransaction();
		
		String username = credentials.getUsername();
		DBLoginCredentials existing = getInternal(entityManager, username);
		
		if(existing != null){
			dbTransactionHelper.endTransaction(entityManager);
			throw new Exception("There is already a user with the name " + username);
		}
		
		entityManager.persist(credentials);
		dbTransactionHelper.endTransaction(entityManager);
	}
	
	public DBLoginCredentials get(String username){
		EntityManager entityManager = dbTransactionHelper.beginTransaction();
		DBLoginCredentials lc = getInternal(entityManager, username);
		dbTransactionHelper.endTransaction(entityManager);
		
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
