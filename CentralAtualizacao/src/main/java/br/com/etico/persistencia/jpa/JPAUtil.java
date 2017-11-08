package br.com.etico.persistencia.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.Session;

import br.com.etico.modelo.beans.Postagem;

public class JPAUtil {

	private static EntityManagerFactory factory;

	static {
		factory = Persistence.createEntityManagerFactory("EticoPU");
	}

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

	public static void close() {
		factory.close();
	}
	
	
	/**
	 * 
	 * Retorna or.hibernate.session
	 * 
	 * No qual pode usar o criteria nativo do Hibernate
	 * 
	 * getSession().createCriteria(Classe.class);
	 * 
	 * 
	 * @return or.hibernate.session
	 */
	public static Session getSession() {
		return (Session) getEntityManager().getDelegate();
	}
	
	
	public static void main(String[] args) {
		
		
		EntityManager manager = getEntityManager();
		EntityTransaction tx = manager.getTransaction();
		
		tx.begin();
		
		Postagem postagem = new Postagem();
		postagem.setTituloPostagem("legal será que vai funcionar");
		
		manager.persist(postagem);
		
		tx.commit();
		
		List<Postagem> postagems = getSession().createCriteria(Postagem.class).list();
		
		for (Postagem postagem2 : postagems) {
			System.out.println(postagem2.getTituloPostagem());
		}
		
	}

}
