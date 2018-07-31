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
import com.iktpreobuka.elektronskiDnevnik.dto.PredajeDTO;
import com.iktpreobuka.elektronskiDnevnik.entities.DrziNastavuEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.NastavnikEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.PredajeEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.PredmetEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.UcenikEntity;
import com.iktpreobuka.elektronskiDnevnik.repositories.NastavnikRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.PredajeRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.PredmetRepository;

@RestController 
@RequestMapping(path = "/api/predaje")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders="*")
public class PredajeController {
	
	@Autowired
	private PredajeRepository predajeRepository;
	
	@Autowired
	private PredmetRepository predmetRepository;

	@Autowired
	private NastavnikRepository nastavnikRepository;
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewPredaje(@Valid @RequestBody PredajeDTO noviPredaje, BindingResult result) {
		if(result.hasErrors()) { 
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST); 
		} 
		if (!predmetRepository.findById(noviPredaje.getIdPredmet()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Predmet nije pronadjen"), HttpStatus.NOT_FOUND);
		}
		PredmetEntity predmet = predmetRepository.findById(noviPredaje.getIdPredmet()).get();
		
		if (!nastavnikRepository.findById(noviPredaje.getIdNastavnik()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Nastavnik nije pronadjen"), HttpStatus.NOT_FOUND);
		} 
		NastavnikEntity nastavnik = nastavnikRepository.findById(noviPredaje.getIdNastavnik()).get();

		PredajeEntity predaje = new PredajeEntity();
		
		predaje.setPredmet(predmet);
		predaje.setNastavnik(nastavnik);
		
		predajeRepository.save(predaje);
		
		return new ResponseEntity<>(predaje, HttpStatus.OK);
	}
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updatePredaje(@Valid @RequestBody PredajeDTO updatePredaje, BindingResult result,
			@PathVariable String id) {
		if(result.hasErrors()) { 
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST); 
		}
		
			if (!predajeRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError(1, "Predaje nije pronadjen"), HttpStatus.NOT_FOUND);
			}			
			PredajeEntity predaje = predajeRepository.findById(Integer.parseInt(id)).get();
			if (!predmetRepository.findById(updatePredaje.getIdPredmet()).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError(1, "Predmet nije pronadjen"), HttpStatus.NOT_FOUND);
				}
			PredmetEntity predmet = predmetRepository.findById(updatePredaje.getIdPredmet()).get();
		
			if (!nastavnikRepository.findById(updatePredaje.getIdNastavnik()).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError(1, "Nastavnik nije pronadjen"), HttpStatus.NOT_FOUND);
				} 
		NastavnikEntity nastavnik = nastavnikRepository.findById(updatePredaje.getIdNastavnik()).get();
		
		predaje.setPredmet(predmet);
		predaje.setNastavnik(nastavnik);
		
		predajeRepository.save(predaje);
		return new ResponseEntity<PredajeEntity>(predaje, HttpStatus.OK);
	} 
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deletePredajeById(@PathVariable String id) {
		try {
			if (predajeRepository.existsById(Integer.parseInt(id))) {
				PredajeEntity predaje = predajeRepository.findById(Integer.parseInt(id)).get();

				predajeRepository.delete(predaje);
					return new ResponseEntity<PredajeEntity>(predaje, HttpStatus.OK);
				}
			
			return new ResponseEntity<RESTError>(new RESTError(1, "Predaje sa prosledjenim id nije pronadjen"),
					HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllPredaje() {
		try {
			return new ResponseEntity<Iterable<PredajeEntity>>(predajeRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getPredajeById(@PathVariable String id) {
		try {
			if(predajeRepository.findById(Integer.parseInt(id)).isPresent()) {
					return new ResponseEntity<PredajeEntity>(predajeRepository.findById(Integer.parseInt(id)).get(), HttpStatus.OK);
				} else {
					return new ResponseEntity<RESTError>(new RESTError(1, "Predaje id nije pronadjen"), HttpStatus.NOT_FOUND);
				}
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/predaje/{id}")
	public ResponseEntity<?> findDrziNastavuByPredaje(@PathVariable String id) {
		
		try {
		if (predajeRepository.existsById(Integer.parseInt(id))) {
		} return new ResponseEntity<List<DrziNastavuEntity>>(predajeRepository.findById(Integer.parseInt(id)).get().getDrziNastavu(), 
				HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
