package com.iktpreobuka.elektronskiDnevnik.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskiDnevnik.entities.OcenaEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.UcenikEntity;

@Service
public class OcenaDaoImpl implements OcenaDao{
	
	@PersistenceContext
    EntityManager em;

	@Override
	public List<OcenaEntity> findOcenaByUcenik(Integer id) {
		String sql = "select a " +
				"from OcenaEntity a " +
				"left join fetch a.ucenik u " +
				"where u.id= :id  ";
		
		Query query = em.createQuery(sql);
        query.setParameter("id", id);
        
        List<OcenaEntity> result = new ArrayList<OcenaEntity>();
        result = query.getResultList();
        return result;
	}
	
}
