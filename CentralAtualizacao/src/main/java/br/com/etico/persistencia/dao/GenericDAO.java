package br.com.etico.persistencia.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.etico.persistencia.EntidadePersistence;
import br.com.etico.persistencia.jpa.Transactional;

@SuppressWarnings("serial")
public abstract class GenericDAO<T> implements Serializable {

	@SuppressWarnings("unused")
	private Session session;
	private Class<?> classe;

	@Inject
	private EntityManager manager;

	@Transactional
	public void inserir(EntidadePersistence objeto) {
		manager.persist(objeto);
	}

	@Transactional
	public void editar(EntidadePersistence objeto) {
		manager.merge(objeto);
	}

	@Transactional
	public void deletar(EntidadePersistence objeto) {
		manager.remove(manager.getReference(objeto.getClass(), objeto.getId()));
	}

	public EntidadePersistence getRegistro(EntidadePersistence objeto) {
		return (EntidadePersistence) getSession().createCriteria(getClasse())
				.add(Restrictions.eq(EntidadePersistence.strId, objeto.getId())).uniqueResult();
	}

	public EntidadePersistence getRegistro(Long id) {
		return (EntidadePersistence) getSession().createCriteria(getClasse())
				.add(Restrictions.eq(EntidadePersistence.strId, id)).uniqueResult();
	}

	/**
	 * 
	 * Obtem todos os registros
	 * 
	 * @return
	 */
	public List getRegistros() {
		return getSession().createCriteria(getClasse()).list();
	}

	public GenericDAO(Class<?> classe) {
		this.setClasse(classe);
	}

	public GenericDAO(Class<?> classe, Session sessao) {
		this.setClasse(classe);
		this.setSession(sessao);
	}

	public Criteria getCriteria() {
		return getSession().createCriteria(getObjectClass());
	}

	public Session getSession() {
		return (Session) getManager().getDelegate();
	}

	public Class<?> getObjectClass() {
		return classe;
	}

	public Class<?> getClasse() {
		return classe;
	}

	public void setClasse(Class<?> classe) {
		this.classe = classe;
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
