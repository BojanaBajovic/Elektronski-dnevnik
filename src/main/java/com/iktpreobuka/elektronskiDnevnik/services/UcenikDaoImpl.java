package com.iktpreobuka.elektronskiDnevnik.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskiDnevnik.entities.UcenikEntity;

@Service
public class UcenikDaoImpl implements UcenikDao{
	
	@PersistenceContext
    EntityManager em;

	@Override
	public List<UcenikEntity> findUcenikByodeljenje(Integer id) {
		String sql = "select a " +
				"from UcenikEntity a " +
				"left join fetch a.odeljenje u " +
				"where u.id= :id  ";
		
		Query query = em.createQuery(sql);
        query.setParameter("id", id);
        
        List<UcenikEntity> result = new ArrayList<UcenikEntity>();
        result = query.getResultList();
        return result;
	}
	
	@Override
	public List<UcenikEntity> findUcenikByodeljenjeIRazred(Integer id, Integer brojRazreda) {
		String sql = "select a " +
				"from UcenikEntity a " +
				"left join fetch a.odeljenje u left join fetch u.razred r " +
				"where u.id= :id and r.brojRazreda= :brojRazreda  ";
		
		Query query = em.createQuery(sql);
        query.setParameter("id", id);
        query.setParameter("brojRazreda", brojRazreda);
        
        List<UcenikEntity> result = new ArrayList<UcenikEntity>();
        result = query.getResultList();
        return result;
	}

	@Override
	public List<UcenikEntity> findUcenikByroditelj(Integer id) {
		String sql = "select a " +
				"from UcenikEntity a " +
				"left join fetch a.roditelj u " +
				"where u.id= :id  ";
		
		Query query = em.createQuery(sql);
        query.setParameter("id", id);
        
        List<UcenikEntity> result = new ArrayList<UcenikEntity>();
        result = query.getResultList();
        return result;
	}

	@Override
	public List<UcenikEntity> findUcenikByodeljenjeSort(Integer id) {
		String sql = "select a " +
				"from UcenikEntity a " +
				"left join fetch a.odeljenje u " +
				"where u.id= :id"+ " order by a.ime asc";
		
		Query query = em.createQuery(sql);
        query.setParameter("id", id);
        
        List<UcenikEntity> result = new ArrayList<UcenikEntity>();
        result = query.getResultList();
        return result;
	}
	
	@Override
	public List<UcenikEntity> findAllByOrderByPrezimeAsc(Integer id) {
		String sql = "select a " +
				"from UcenikEntity a " +
				"left join fetch a.odeljenje u " +
				"where u.id= :id"+ " order by a.prezime asc";
		
		Query query = em.createQuery(sql);
        query.setParameter("id", id);
        
        List<UcenikEntity> result = new ArrayList<UcenikEntity>();
        result = query.getResultList();
        return result;
	}


}
