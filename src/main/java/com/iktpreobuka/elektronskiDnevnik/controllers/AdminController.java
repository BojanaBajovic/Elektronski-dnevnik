package com.iktpreobuka.elektronskiDnevnik.controllers;

import java.util.stream.Collectors;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskiDnevnik.controllers.util.AdminValidator;
import com.iktpreobuka.elektronskiDnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronskiDnevnik.dto.AdminDTO;
import com.iktpreobuka.elektronskiDnevnik.entities.AdminEntity;
import com.iktpreobuka.elektronskiDnevnik.enumerations.EKorisnikRole;
import com.iktpreobuka.elektronskiDnevnik.repositories.AdminRepository;
import com.iktpreobuka.elektronskiDnevnik.services.LogovanjeAdmin;

@RestController
@RequestMapping(path = "/api/admin")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders="*")
public class AdminController {
	
	@Autowired
	private AdminRepository adminRepository;

	@Autowired AdminValidator adminValidator;
	
	@Autowired
	private LogovanjeAdmin logovanjeAdmin;
	
	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(adminValidator);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewAdmin(@Valid @RequestBody AdminDTO noviAdmin, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			adminValidator.validate(noviAdmin, result);
		}

		AdminEntity admin = new AdminEntity();

		admin.setUsername(noviAdmin.getUsername());
		admin.setPassword(noviAdmin.getPassword());
		admin.setIme(noviAdmin.getIme());
		admin.setPrezime(noviAdmin.getPrezime());
		admin.setEmail(noviAdmin.getEmail());
		admin.setKorisnikRole(EKorisnikRole.ROLE_ADMIN);
		adminRepository.save(admin);
		return new ResponseEntity<>(admin, HttpStatus.OK);
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateAdmin(@Valid @RequestBody AdminDTO updateAdmin, BindingResult result,
			@PathVariable String id) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			adminValidator.validate(updateAdmin, result);
		}
		try {
			if (adminRepository.existsById(Integer.parseInt(id))) {
		
				AdminEntity admin = adminRepository.findById(Integer.parseInt(id)).get();
		
		admin.setUsername(updateAdmin.getUsername());
		admin.setPassword(updateAdmin.getPassword());
		admin.setIme(updateAdmin.getIme());
		admin.setPrezime(updateAdmin.getPrezime());
		admin.setEmail(updateAdmin.getEmail());
		return new ResponseEntity<AdminEntity>(adminRepository.save(admin), HttpStatus.OK);
	}
		return new ResponseEntity<RESTError>(new RESTError(1, "Admin sa unetim ID-jem nije pronađen."),
					HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteAdmin(@PathVariable String id) {
		try {
			if (adminRepository.existsById(Integer.parseInt(id))) {
				AdminEntity admin = adminRepository.findById(Integer.parseInt(id)).get();

					adminRepository.delete(admin);
					return new ResponseEntity<AdminEntity>(admin, HttpStatus.OK);
				}
			return new ResponseEntity<RESTError>(new RESTError(1, "Admin sa prosledjenim id nije pronadjen"),
					HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllAdmin() {
		try {
			return new ResponseEntity<Iterable<AdminEntity>>(adminRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getAdminById(@PathVariable String id) {
		try {
			if (adminRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<AdminEntity>(adminRepository.findById(Integer.parseInt(id)).get(), HttpStatus.OK);
				} else {
			return new ResponseEntity<RESTError>(new RESTError(1, "Admin nije pronadjen"), HttpStatus.NOT_FOUND);
				} 
			} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/login")
	public ResponseEntity<String> login(@RequestBody AdminDTO adminDTO) {
		AdminEntity adminEntity = logovanjeAdmin.findByUsername(adminDTO.getUsername());
		if (adminEntity == null) {
			return new ResponseEntity<>("Username doesn't exist", HttpStatus.BAD_REQUEST);
		}
		
		if (adminEntity.getPassword().equals(adminDTO.getPassword())) {
			return new ResponseEntity<>(adminEntity.getUsername(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Wrong password", HttpStatus.BAD_REQUEST);
		}		
	}
}
