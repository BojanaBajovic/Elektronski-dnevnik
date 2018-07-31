package com.iktpreobuka.elektronskiDnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskiDnevnik.entities.RoditeljEntity;
import com.iktpreobuka.elektronskiDnevnik.repositories.RoditeljRepository;

	@Service
	public class LogovanjeRoditelj {
		
		@Autowired
		private RoditeljRepository roditeljRepository;
		
		public RoditeljEntity findByUsername(String username) {
			return roditeljRepository.findByUsername(username);
		}
	}

