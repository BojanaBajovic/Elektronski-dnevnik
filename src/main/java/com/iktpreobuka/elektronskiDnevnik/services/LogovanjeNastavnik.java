package com.iktpreobuka.elektronskiDnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskiDnevnik.entities.NastavnikEntity;
import com.iktpreobuka.elektronskiDnevnik.repositories.NastavnikRepository;

@Service
public class LogovanjeNastavnik {
	
	@Autowired
	private NastavnikRepository nastavnikRepository;
	
	public NastavnikEntity findByUsername(String username) {
		return nastavnikRepository.findByUsername(username);
	}
}
