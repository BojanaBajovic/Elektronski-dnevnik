package com.iktpreobuka.elektronskiDnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskiDnevnik.entities.AdminEntity;
import com.iktpreobuka.elektronskiDnevnik.repositories.AdminRepository;

@Service
public class LogovanjeAdmin {
	
	@Autowired
	private AdminRepository adminRepository;
	
	public AdminEntity findByUsername(String username) {
		return adminRepository.findByUsername(username);
	}
}
