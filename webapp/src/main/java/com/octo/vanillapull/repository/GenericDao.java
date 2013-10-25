package com.octo.vanillapull.repository;

import org.springframework.cglib.proxy.Enhancer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericDao<Key, Entity> {

    @PersistenceContext
    EntityManager entityManager;

	protected Class<Entity> entityClass;

	@SuppressWarnings("unchecked")
	public GenericDao() {
    // Skip initialization if it's a proxy
    if(Enhancer.isEnhanced(getClass())) {
      return;
    }
		ParameterizedType genericSuperclass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		this.entityClass = (Class<Entity>) genericSuperclass
				.getActualTypeArguments()[1];
	}

	public void persist(Entity entity) {
		entityManager.persist(entity);
	}

	public void remove(Entity entity) {
        entityManager.remove(entity);
	}

	public Entity merge(Entity entity) {
		return entityManager.merge(entity);
	}

	public void refresh(Entity entity) {
        entityManager.refresh(entity);
	}

	public Entity findById(Key id) {
		return entityManager.find(entityClass, id);
	}

	public void flush() {
        entityManager.flush();
	}

	public List<Entity> findAll() {
		List<Entity> res = entityManager.createQuery("select h from " + entityClass.getName() + " h").getResultList();
		return res;
	}

	public int removeAll() {
		return entityManager.createQuery("delete from " + entityClass.getName() + " h").executeUpdate();
	}

}
