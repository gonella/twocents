package com.twocents.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.twocents.model.Persistent;

public abstract class BaseDAOImpl<T extends Persistent> implements BaseDAO<T> {

	private static final Logger log = Logger.getLogger(BaseDAOImpl.class);

	@PersistenceContext
	private EntityManager em;
	private Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public BaseDAOImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

	}

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	protected EntityManager getEntityManager() {
		return em;
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public void attachClean(T instance) {
		getEntityManager().lock(instance, LockModeType.READ);
	}

	public void delete(T persistentInstance) {
		log.debug("deleting Empresa instance");
		try {
			persistentInstance = merge(persistentInstance);
			getEntityManager().remove(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}

	}

	public void deleteById(Long id) {
		T e = findById(id);
		delete(e);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Query query = getEntityManager().createQuery(
				"select e FROM " + getPersistentClass().getName() + " e");
		return query.getResultList();
	}

	public T findById(Long id) {
		log.debug("getting instance with id: " + id);
		try {
			T instance = getEntityManager().find(getPersistentClass(), id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public T merge(T detachedInstance) {
		log.debug("merging instance: " + getPersistentClass().getSimpleName());
		try {
			T result = getEntityManager().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void persist(T transientInstance) {
		log.debug("persisting instance: "
				+ getPersistentClass().getSimpleName());
		try {
			if (transientInstance.getId() != null) {
				transientInstance = merge(transientInstance);
			} else {
				getEntityManager().persist(transientInstance);
			}

			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}

	}

}
