package com.iktpreobuka.elektronskiDnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskiDnevnik.entities.UcenikEntity;
import com.iktpreobuka.elektronskiDnevnik.repositories.UcenikRepository;

@Service
public class LogovanjeUcenik {
	
	@Autowired
	private UcenikRepository ucenikRepository;
	
	public UcenikEntity findByUsername(String username) {
		return ucenikRepository.findByUsername(username);
	}
}
