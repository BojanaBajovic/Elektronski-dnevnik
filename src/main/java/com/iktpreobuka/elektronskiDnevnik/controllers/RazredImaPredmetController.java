package com.iktpreobuka.elektronskiDnevnik.controllers;

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
import com.iktpreobuka.elektronskiDnevnik.dto.RazredImaPredmetDTO;
import com.iktpreobuka.elektronskiDnevnik.entities.NastavnikEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.PredajeEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.PredmetEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.RazredEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.RazredImaPredmetEntity;
import com.iktpreobuka.elektronskiDnevnik.repositories.PredmetRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.RazredImaPredmetRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.RazredRepository;

@RestController
@RequestMapping(path = "/api/razredImaPredmet")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders="*")

public class RazredImaPredmetController {

	@Autowired
	private PredmetRepository predmetRepository;

	@Autowired
	private RazredRepository razredRepository;

	@Autowired
	private RazredImaPredmetRepository razredImaPredmetRepository;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewRazredImaPredmet(@Valid @RequestBody RazredImaPredmetDTO noviRazredImaPredmet, BindingResult result) {
		if(result.hasErrors()) { 
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST); 
		} 
		if (!predmetRepository.findById(noviRazredImaPredmet.getIdPredmet()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Predmet nije pronadjen"), HttpStatus.NOT_FOUND);
		}
		PredmetEntity predmet = predmetRepository.findById(noviRazredImaPredmet.getIdPredmet()).get();
		
		if (!razredRepository.findById(noviRazredImaPredmet.getIdRazred()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Razred nije pronadjen"), HttpStatus.NOT_FOUND);
		} 
		RazredEntity razred = razredRepository.findById(noviRazredImaPredmet.getIdRazred()).get();

		RazredImaPredmetEntity razredImaPredmet = new RazredImaPredmetEntity();
		
		razredImaPredmet.setPredmet(predmet);
		razredImaPredmet.setRazred(razred);
		
		razredImaPredmetRepository.save(razredImaPredmet);
		
		return new ResponseEntity<>(razredImaPredmet, HttpStatus.OK);
	}
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateRazredImaPredmet(@Valid @RequestBody RazredImaPredmetDTO updateRazredImaPredmet, BindingResult result,
			@PathVariable String id) {
		if(result.hasErrors()) { 
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST); 
		}
		
			if (!razredImaPredmetRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError(1, "Razred ima predmet id nije pronadjen"), HttpStatus.NOT_FOUND);
			}			
			RazredImaPredmetEntity razredImaPredmet = razredImaPredmetRepository.findById(Integer.parseInt(id)).get();
			
			if (!predmetRepository.findById(updateRazredImaPredmet.getIdPredmet()).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError(1, "Predmet nije pronadjen"), HttpStatus.NOT_FOUND);
				}
			PredmetEntity predmet = predmetRepository.findById(updateRazredImaPredmet.getIdPredmet()).get();
		
			if (!razredRepository.findById(updateRazredImaPredmet.getIdRazred()).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError(1, "Razred nije pronadjen"), HttpStatus.NOT_FOUND);
				} 
		RazredEntity razred = razredRepository.findById(updateRazredImaPredmet.getIdRazred()).get();
		
		razredImaPredmet.setPredmet(predmet);
		razredImaPredmet.setRazred(razred);
		
		razredImaPredmetRepository.save(razredImaPredmet);
		return new ResponseEntity<RazredImaPredmetEntity>(razredImaPredmet, HttpStatus.OK);
	} 
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteRazredImaPredmetById(@PathVariable String id) {
		try {
			if (razredImaPredmetRepository.existsById(Integer.parseInt(id))) {
				RazredImaPredmetEntity razredImaPredmet = razredImaPredmetRepository.findById(Integer.parseInt(id)).get();

				razredImaPredmetRepository.delete(razredImaPredmet);
					return new ResponseEntity<RazredImaPredmetEntity>(razredImaPredmet, HttpStatus.OK);
				}
			
			return new ResponseEntity<RESTError>(new RESTError(1, "Razred ima predmet sa prosledjenim id nije pronadjen"),
					HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllRazredImaPredmet() {
		try {
			return new ResponseEntity<Iterable<RazredImaPredmetEntity>>(razredImaPredmetRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getRazredImaPredmetById(@PathVariable String id) {
		try {
			if(razredImaPredmetRepository.findById(Integer.parseInt(id)).isPresent()) {
					return new ResponseEntity<RazredImaPredmetEntity>(razredImaPredmetRepository.findById(Integer.parseInt(id)).get(), HttpStatus.OK);
				} else {
					return new ResponseEntity<RESTError>(new RESTError(1, "Razred ima predmet id nije pronadjen"), HttpStatus.NOT_FOUND);
				}
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	/*@RequestMapping(method = RequestMethod.POST, value = "/predmet/{predmetId}/razred/{razredId}")
	public ResponseEntity<?> addNewRazredImaPredmet(@PathVariable Integer predmetId, @PathVariable Integer razredId) {

		RazredImaPredmetEntity razredImaPredmet = new RazredImaPredmetEntity();
		if (!predmetRepository.findById(predmetId).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Predaje ID not found"), HttpStatus.NOT_FOUND);
		}

		if (!razredRepository.findById(razredId).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Odeljenje ID not found."), HttpStatus.NOT_FOUND);
		}

		PredmetEntity predmet = predmetRepository.findById(predmetId).get();
		RazredEntity razred = razredRepository.findById(razredId).get();

		razredImaPredmet.setPredmet(predmet);
		razredImaPredmet.setRazred(razred);

		// drziNastavudao.findDrziNastavuByPredajeOdeljnje(predajeId, odeljenjeId);
		// drziNastavudao.findDrziNastavuByPredajeOdeljnje(predajeId, odeljenjeId);
		return new ResponseEntity<RazredImaPredmetEntity>(razredImaPredmetRepository.save(razredImaPredmet), HttpStatus.OK);
	}*/
}
