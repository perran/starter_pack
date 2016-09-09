package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class DbTransactionHelper {
	
	private EntityManagerFactory entityManagerFactory;

	public DbTransactionHelper(EntityManagerFactory entityManagerFactory) {
		super();
		this.entityManagerFactory = entityManagerFactory;
	}
	
	public EntityManager beginTransaction(){
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		return entityManager;
	}
	
	public void endTransaction(EntityManager entityManager){
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
