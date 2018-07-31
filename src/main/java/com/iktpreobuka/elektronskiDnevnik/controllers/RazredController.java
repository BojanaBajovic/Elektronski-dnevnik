package com.iktpreobuka.elektronskiDnevnik.controllers;

import java.util.List;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskiDnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronskiDnevnik.dto.RazredDTO;
import com.iktpreobuka.elektronskiDnevnik.entities.OdeljenjeEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.RazredEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.RazredImaPredmetEntity;
import com.iktpreobuka.elektronskiDnevnik.repositories.RazredRepository;

@RestController 
@RequestMapping(path = "/api/razred")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders="*")

public class RazredController {
	
	@Autowired
	private RazredRepository razredRepository;
	
	/*@Autowired
	private OdeljenjeRepository odeljenjeRepository;*/
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewRazred(@Valid @RequestBody RazredDTO noviRazred, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}	
			RazredEntity razred = new RazredEntity();
			
			razred.setBrojRazreda(noviRazred.getBrojRazreda());
			razredRepository.save(razred);
			return new ResponseEntity<>(razred, HttpStatus.OK);
		} 
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	//Ovde radi sve, validacija i provere za string. Tako isto uraditi i ostale putove
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateRazred(@Valid @RequestBody RazredDTO updateRazred, BindingResult result,
			@PathVariable String id) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
		if (razredRepository.existsById(Integer.parseInt(id))) {
			RazredEntity razred = razredRepository.findById(Integer.parseInt(id)).get();
		
		razred.setBrojRazreda(updateRazred.getBrojRazreda());
		return new ResponseEntity<RazredEntity>(razredRepository.save(razred), HttpStatus.OK);
	}
		return new ResponseEntity<RESTError>(new RESTError(1, "Razred sa unetim ID-jem nije pronađen."),
				HttpStatus.NOT_FOUND);
	} catch (Exception e) {
		return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteRazred(@PathVariable String id) {
		try {
			if (razredRepository.existsById(Integer.parseInt(id))) {
				RazredEntity razred = razredRepository.findById(Integer.parseInt(id)).get();

				razredRepository.delete(razred);
					return new ResponseEntity<RazredEntity>(razred, HttpStatus.OK);
				}
			return new ResponseEntity<RESTError>(new RESTError(1, "Razred sa prosledjenim id nije pronadjen"),
					HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllRazred(){ 
		try {
			return new ResponseEntity<Iterable<RazredEntity>>(razredRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getRazredById(@PathVariable String id) {
		try {
			if(razredRepository.findById(Integer.parseInt(id)).isPresent()) {
					return new ResponseEntity<RazredEntity>(razredRepository.findById(Integer.parseInt(id)).get(), HttpStatus.OK);
				} else {
					return new ResponseEntity<RESTError>(new RESTError(1, "Razred nije pronadjen"), HttpStatus.NOT_FOUND);
				}
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/razredOdeljenje/{id}")
	public ResponseEntity<?> findOdeljenjeByRazred(@PathVariable String id) {
		
		try {
		if (razredRepository.existsById(Integer.parseInt(id))) {
		} return new ResponseEntity<List<OdeljenjeEntity>>(razredRepository.findById(Integer.parseInt(id)).get().getOdeljenje(), 
				HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/razredImaPredmet/{id}")
	public ResponseEntity<?> findRazredImaPredmetByRazred(@PathVariable String id) {
		
		try {
		if (razredRepository.existsById(Integer.parseInt(id))) {
		} return new ResponseEntity<List<RazredImaPredmetEntity>>(razredRepository.findById(Integer.parseInt(id)).get().getRazredImaPredmet(), 
				HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}


