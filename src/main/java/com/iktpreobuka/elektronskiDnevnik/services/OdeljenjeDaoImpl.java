package com.iktpreobuka.elektronskiDnevnik.services;

import java.util.ArrayList;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskiDnevnik.entities.OdeljenjeEntity;

@Service 
public class OdeljenjeDaoImpl implements OdeljenjeDao{ 
	 
	@PersistenceContext    
	EntityManager em; 
	 
	   @Override 
	   public OdeljenjeEntity findOdeljenjeByName(String name) {  
		   String sql = "select a " + "from OdeljenjeEntity a " + "left join fetch a.ucenik u " +  "where u.name = :name "; 
	 
	Query query = em.createQuery(sql);         
	query.setParameter("name", name);                  
	List<OdeljenjeEntity> result = new ArrayList<>();         
	result = query.getResultList();         
	return (OdeljenjeEntity) result; 
	}
}
