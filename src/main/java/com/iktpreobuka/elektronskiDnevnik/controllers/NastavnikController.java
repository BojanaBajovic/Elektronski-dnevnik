package com.iktpreobuka.elektronskiDnevnik.controllers;

import java.util.List;


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

import com.iktpreobuka.elektronskiDnevnik.controllers.util.NastavnikValidator;
import com.iktpreobuka.elektronskiDnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronskiDnevnik.dto.NastavnikDTO;
import com.iktpreobuka.elektronskiDnevnik.entities.DrziNastavuEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.NastavnikEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.PredajeEntity;
import com.iktpreobuka.elektronskiDnevnik.enumerations.EKorisnikRole;
import com.iktpreobuka.elektronskiDnevnik.repositories.NastavnikRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.PredajeRepository;
import com.iktpreobuka.elektronskiDnevnik.services.LogovanjeNastavnik;

@RestController 
@RequestMapping(path = "/api/nastavnik")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders="*")


public class NastavnikController {
	
	@Autowired
	private NastavnikRepository nastavnikRepository;
	
	@Autowired
	private PredajeRepository predajeRepository;
	
	@Autowired
	private LogovanjeNastavnik logovanjeNastavnik;
	
	@Autowired NastavnikValidator nastavnikValidator;


	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(nastavnikValidator);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewNastavnik(@Valid @RequestBody NastavnikDTO noviNastavnik, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			nastavnikValidator.validate(noviNastavnik, result);
		}

		NastavnikEntity nastavnik = new NastavnikEntity();

		nastavnik.setUsername(noviNastavnik.getUsername());
		nastavnik.setPassword(noviNastavnik.getPassword());
		nastavnik.setIme(noviNastavnik.getIme());
		nastavnik.setPrezime(noviNastavnik.getPrezime());
		nastavnik.setEmail(noviNastavnik.getEmail());
		nastavnik.setKorisnikRole(EKorisnikRole.ROLE_NASTAVNIK);
	
		nastavnikRepository.save(nastavnik);
		return new ResponseEntity<>(nastavnik, HttpStatus.OK);
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateNastavnik(@Valid @RequestBody NastavnikDTO updateNastavnik, BindingResult result,
			@PathVariable String id) {
		
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			nastavnikValidator.validate(updateNastavnik, result);
		}
		try {
			if (nastavnikRepository.findById(Integer.parseInt(id)).isPresent()) {

		NastavnikEntity nastavnik = nastavnikRepository.findById(Integer.parseInt(id)).get();
		
		nastavnik.setUsername(updateNastavnik.getUsername());
		nastavnik.setPassword(updateNastavnik.getPassword());
		nastavnik.setIme(updateNastavnik.getIme());
		nastavnik.setPrezime(updateNastavnik.getPrezime());
		nastavnik.setEmail(updateNastavnik.getEmail());
		return new ResponseEntity<NastavnikEntity>(nastavnikRepository.save(nastavnik), HttpStatus.OK);
	}
		return new ResponseEntity<RESTError>(new RESTError(1, "Nastavnik sa unetim ID-jem nije pronađen."),
					HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllNastavnik() {
		try {
			return new ResponseEntity<Iterable<NastavnikEntity>>(nastavnikRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getNastavnikById(@PathVariable String id) {
		try {
			if (nastavnikRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<NastavnikEntity>(nastavnikRepository.findById(Integer.parseInt(id)).get(), HttpStatus.OK);
				} else {
			return new ResponseEntity<RESTError>(new RESTError(1, "Nastavnik nije pronadjen"), HttpStatus.NOT_FOUND);
				} 
			} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Dogodila se greška: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> obrisiNastavnikaById(@PathVariable String id) {
		try {
			if (nastavnikRepository.existsById(Integer.parseInt(id))) {
				NastavnikEntity nastavnik = nastavnikRepository.findById(Integer.parseInt(id)).get();

					nastavnikRepository.delete(nastavnik);
					return new ResponseEntity<NastavnikEntity>(nastavnik, HttpStatus.OK);
				}
			return new ResponseEntity<RESTError>(new RESTError(1, "Nastavnik sa prosledjenim id nije pronadjen"),
					HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Izlistava podatke od nastavnika i predmet koji predaje kroz predaje
	@RequestMapping(method = RequestMethod.GET, value = "/nastavnik/{id}")
	public ResponseEntity<?> findPredajeByNastavnik(@PathVariable String id) {
		
		try {
		if (nastavnikRepository.existsById(Integer.parseInt(id))) {
		} return new ResponseEntity<List<PredajeEntity>>(nastavnikRepository.findById(Integer.parseInt(id)).get().getPredaje(), 
				HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//OVDE IZLISTAVA PREDAJE I DRZI NASTAVU ODNOSNO LISTE Nastavnika, predmet i odeljenje kom predaje
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
	
	// na osnovu id predaje izlistaj mi nastavnika
		@RequestMapping(method = RequestMethod.GET, value = "/predajee/{id}")
		public ResponseEntity<?> findNastavnikByPredaje(@PathVariable String id) {
		try {
			if (predajeRepository.existsById(Integer.parseInt(id))) {
			} return new ResponseEntity<NastavnikEntity>(predajeRepository.findById(Integer.parseInt(id)).get().getNastavnik(), 
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@RequestMapping(method=RequestMethod.POST, value="/login")
		public ResponseEntity<String> login(@RequestBody NastavnikDTO nastavnikDTO) {
			NastavnikEntity nastavnikEntity = logovanjeNastavnik.findByUsername(nastavnikDTO.getUsername());
			if (nastavnikEntity == null) {
				return new ResponseEntity<>("Username doesn't exist", HttpStatus.BAD_REQUEST);
			}
			
			if (nastavnikEntity.getPassword().equals(nastavnikDTO.getPassword())) {
				return new ResponseEntity<>(nastavnikEntity.getUsername(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Wrong password", HttpStatus.BAD_REQUEST);
			}
		}
		
		
		/*@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
		public ResponseEntity<?> updateNastavnik(@PathVariable String id, @RequestBody NastavnikDTO updateNastavnik) {
		try {
			if (!nastavnikRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError(1, "Nastavnik sa unetim ID-jem nije pronađen."),
						HttpStatus.NOT_FOUND);
			}
			NastavnikEntity nastavnik = nastavnikRepository.findById(Integer.parseInt(id)).get();

			if (updateNastavnik.getUsername() != null || !updateNastavnik.getUsername().equals(" ")
					|| !updateNastavnik.getUsername().equals("")) {
				nastavnik.setUsername(updateNastavnik.getUsername());
			}
			if (updateNastavnik.getPassword() != null || !updateNastavnik.getPassword().equals(" ")
					|| !updateNastavnik.getPassword().equals("")) {
				nastavnik.setPassword(updateNastavnik.getPassword());
			}

			if (updateNastavnik.getIme() != null || !updateNastavnik.getIme().equals(" ")
					|| !updateNastavnik.getIme().equals("")) {
				nastavnik.setIme(updateNastavnik.getIme());
			}
			if (updateNastavnik.getPrezime() != null || !updateNastavnik.getPrezime().equals(" ")
					|| !updateNastavnik.getPrezime().equals("")) {
				nastavnik.setPrezime(updateNastavnik.getPrezime());
			}
			if (updateNastavnik.getEmail() != null || !updateNastavnik.getEmail().equals(" ")
					|| !updateNastavnik.getEmail().equals("")) {
				nastavnik.setEmail(updateNastavnik.getEmail());
			}

			return new ResponseEntity<NastavnikEntity>(nastavnikRepository.save(nastavnik), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}*/
}


