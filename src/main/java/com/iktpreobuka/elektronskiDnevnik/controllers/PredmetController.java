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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskiDnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronskiDnevnik.dto.NastavnikDTO;
import com.iktpreobuka.elektronskiDnevnik.dto.PredmetDTO;
import com.iktpreobuka.elektronskiDnevnik.entities.NastavnikEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.OdeljenjeEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.PredajeEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.PredmetEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.RazredImaPredmetEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.RoditeljEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.UcenikEntity;
import com.iktpreobuka.elektronskiDnevnik.enumerations.EKorisnikRole;
import com.iktpreobuka.elektronskiDnevnik.repositories.NastavnikRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.PredajeRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.PredmetRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.RazredImaPredmetRepository;

@RestController 
@RequestMapping(path = "/api/predmet")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders="*")
public class PredmetController {
	
	@Autowired
	private PredmetRepository predmetRepository;
	
	@Autowired
	private RazredImaPredmetRepository razredImaPredmetRepository;
	
	@Autowired
	private PredajeRepository predajeRepository;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewPredmet(@Valid @RequestBody PredmetDTO noviPredmet, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} 

		PredmetEntity predmet = new PredmetEntity();

		predmet.setNazivPredmeta(noviPredmet.getNazivPredmeta());
		predmet.setFond(noviPredmet.getFond());
	
		predmetRepository.save(predmet);
		return new ResponseEntity<>(predmet, HttpStatus.OK);
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updatePredmet(@Valid @RequestBody PredmetDTO updatePredmet, BindingResult result,
			@PathVariable String id) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		if (!predmetRepository.findById(Integer.parseInt(id)).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Predmet sa unetim ID-jem nije pronađen."),
					HttpStatus.NOT_FOUND);
		}

		PredmetEntity predmet = predmetRepository.findById(Integer.parseInt(id)).get();
		
		predmet.setNazivPredmeta(updatePredmet.getNazivPredmeta());
		predmet.setFond(updatePredmet.getFond());
		return new ResponseEntity<PredmetEntity>(predmetRepository.save(predmet), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllPredmeti() {
		try {
			return new ResponseEntity<Iterable<PredmetEntity>>(predmetRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getPredmetById(@PathVariable String id) {
		try {
			if (predmetRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<PredmetEntity>(predmetRepository.findById(Integer.parseInt(id)).get(), HttpStatus.OK);
				} else {
			return new ResponseEntity<RESTError>(new RESTError(1, "Predmet nije pronadjen"), HttpStatus.NOT_FOUND);
				} 
			} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deletePredmetById(@PathVariable String id) {
		try {
			if (predmetRepository.existsById(Integer.parseInt(id))) {
				PredmetEntity predmet = predmetRepository.findById(Integer.parseInt(id)).get();

				if (!predmet.getPredaje().isEmpty()) {
					return new ResponseEntity<RESTError>(
							new RESTError(2, "Nije dozvoljeno brisanje predmeta sa prosledjenim id !"),
							HttpStatus.BAD_REQUEST);
					}
				if (!predmet.getRazredImaPredmet().isEmpty()) {
					return new ResponseEntity<RESTError>(
							new RESTError(2, "Nije dozvoljeno brisanje predmeta sa prosledjenim id !"),
							HttpStatus.BAD_REQUEST);

				} else {
					predmetRepository.delete(predmet);
					return new ResponseEntity<PredmetEntity>(predmet, HttpStatus.OK);
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Predmet sa prosledjenim id nije pronadjen"),
					HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/predmetPredaje/{id}")
	public ResponseEntity<?> findPredajeNastavuByPredmet(@PathVariable String id) {
		
		try {
		if (predmetRepository.existsById(Integer.parseInt(id))) {
		} return new ResponseEntity<List<PredajeEntity>>(predmetRepository.findById(Integer.parseInt(id)).get().getPredaje(), 
				HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/predmetImaRazred/{id}")
	public ResponseEntity<?> findRazredImaPredmetByPredmet(@PathVariable String id) {
		
		try {
		if (predmetRepository.existsById(Integer.parseInt(id))) {
		} return new ResponseEntity<List<RazredImaPredmetEntity>>(predmetRepository.findById(Integer.parseInt(id)).get().getRazredImaPredmet(), 
				HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
