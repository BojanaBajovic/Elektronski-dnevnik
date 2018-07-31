package com.iktpreobuka.elektronskiDnevnik.controllers;

import java.util.List;

import java.util.Optional;
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
import com.iktpreobuka.elektronskiDnevnik.dto.OdeljenjeDTO;

import com.iktpreobuka.elektronskiDnevnik.entities.OdeljenjeEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.RazredEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.UcenikEntity;
import com.iktpreobuka.elektronskiDnevnik.repositories.OdeljenjeRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.RazredRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.UcenikRepository;

@RestController 
@RequestMapping(path = "/api/odeljenje")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders="*")

public class OdeljenjeController {

	@Autowired
	private UcenikRepository ucenikRepository;
	
	@Autowired
	private OdeljenjeRepository odeljenjeRepository;
	
	@Autowired
	private RazredRepository razredRepository;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewOdeljenjeRazred(@Valid @RequestBody OdeljenjeDTO novoOdeljenje, BindingResult result) {
		if(result.hasErrors()) { 
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST); 
		} 
		if (!razredRepository.findById(novoOdeljenje.getIdRazred()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Razred nije pronadjen"), HttpStatus.NOT_FOUND);
		}
		RazredEntity razred = razredRepository.findById(novoOdeljenje.getIdRazred()).get();
		
		OdeljenjeEntity odeljenje = new OdeljenjeEntity();
		
		odeljenje.setBrojOdeljenja(novoOdeljenje.getBrojOdeljenja());
		odeljenje.setRazred(razred);
		
		odeljenjeRepository.save(odeljenje);
		return new ResponseEntity<>(odeljenje, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateRoditelj(@Valid @RequestBody OdeljenjeDTO updateOdeljenje, BindingResult result,
			@PathVariable String id) {
			if(result.hasErrors()) { 
				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST); 
			}
		if (!odeljenjeRepository.findById(Integer.parseInt(id)).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Odeljenje sa unetim ID-jem nije pronađen."),
					HttpStatus.NOT_FOUND);
		}
		if (!razredRepository.findById(updateOdeljenje.getIdRazred()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Razred nije pronadjen"), HttpStatus.NOT_FOUND);
		}
		RazredEntity razred = razredRepository.findById(updateOdeljenje.getIdRazred()).get();

		OdeljenjeEntity odeljenje = odeljenjeRepository.findById(Integer.parseInt(id)).get();
		
		odeljenje.setBrojOdeljenja(updateOdeljenje.getBrojOdeljenja());
		odeljenje.setRazred(razred);
		return new ResponseEntity<OdeljenjeEntity>(odeljenjeRepository.save(odeljenje), HttpStatus.OK);
	}
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteOdeljenjeById(@PathVariable String id) {
		try {
			if (odeljenjeRepository.existsById(Integer.parseInt(id))) {
				OdeljenjeEntity odeljenje = odeljenjeRepository.findById(Integer.parseInt(id)).get();

				odeljenjeRepository.delete(odeljenje);
					return new ResponseEntity<OdeljenjeEntity>(odeljenje, HttpStatus.OK);
				}
			
			return new ResponseEntity<RESTError>(new RESTError(1, "Odeljenje sa prosledjenim id nije pronadjeno"),
					HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllOdeljenja() {
		try {
			return new ResponseEntity<Iterable<OdeljenjeEntity>>(odeljenjeRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getOdeljenjeById(@PathVariable String id) {
		try {
			if(odeljenjeRepository.findById(Integer.parseInt(id)).isPresent()) {
					return new ResponseEntity<OdeljenjeEntity>(odeljenjeRepository.findById(Integer.parseInt(id)).get(), HttpStatus.OK);
				} else {
					return new ResponseEntity<RESTError>(new RESTError(1, "Odeljenje nije pronadjeno"), HttpStatus.NOT_FOUND);
				}
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//Ovo mozda i ne treba,vec imam gore
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/razred") 
	public OdeljenjeEntity addRazredToOdeljenje(@PathVariable Integer id, @RequestParam Integer razred) { 
		
		Optional<OdeljenjeEntity> odeljenje = odeljenjeRepository.findById(id);
		Optional<RazredEntity> raz = razredRepository.findById(razred);
		
		odeljenje.get().setRazred(raz.get());
		odeljenjeRepository.save(odeljenje.get());
		return odeljenje.get();
	
		}
	
	//Izlistaj mi ucenike odredjenog odeljenja, na osnovu id odeljenja
	@RequestMapping(method = RequestMethod.GET, value = "/odeljenje/{id}")
	public ResponseEntity<?> findUcenikById(@PathVariable String id) {
		try {
			if (odeljenjeRepository.existsById(Integer.parseInt(id))) {
			} return new ResponseEntity<List<UcenikEntity>>(odeljenjeRepository.findById(Integer.parseInt(id)).get().getUcenik(), 
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	// na osnovu id deteta-ucenika izlistaj mi njegovo odeljenje
		@RequestMapping(method = RequestMethod.GET, value = "/ucenik/{id}")
		public ResponseEntity<?> findOdeljenjeByUcenik(@PathVariable String id) {
		try {
			if (ucenikRepository.existsById(Integer.parseInt(id))) {
			} return new ResponseEntity<OdeljenjeEntity>(ucenikRepository.findById(Integer.parseInt(id)).get().getOdeljenje(), 
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	//Izlistaj sve ucenike iz svih odeljenja
	@RequestMapping(method = RequestMethod.GET, value = "/ucenik")
	public Iterable<UcenikEntity> findAllUcenik () {
		
			return ucenikRepository.findAll();
		
	}
	
	@RequestMapping(value = "/ucenici/{id}", method = RequestMethod.GET)
	public List<UcenikEntity> findUcenikByOdeljenje(@PathVariable Integer id) {
		if (odeljenjeRepository.existsById(id)) {
			return odeljenjeRepository.findById(id).get().getUcenik();
		}
		return null;
	}
	
	/*@RequestMapping(method = RequestMethod.GET, value = "/ucenik/{name}")    
	public OdeljenjeEntity findOdeljenjeByName(@PathVariable String name) { 
		if (ucenikRepository.equals(name)) {
		return odeljenjeDao.findOdeljenjeByName(name);
		}
		return null;
	}*/
	
	/*@RequestMapping(method = RequestMethod.POST) 
	public OdeljenjeEntity addNewOdeljenje(@RequestParam Integer id, @RequestParam String brojOdeljenja, 
			@RequestParam List<UcenikEntity> ucenik, @RequestParam RazredEntity razred, @RequestParam List<DrziNastavuEntity> drziNastavu) { 
		
		OdeljenjeEntity odeljenje = new OdeljenjeEntity();
		UcenikEntity uce = new UcenikEntity();
		
		odeljenje.setId(id);
		odeljenje.setBrojOdeljenja(brojOdeljenja);
		odeljenje.setUcenik(ucenik);
		odeljenje.setRazred(razred);
		odeljenje.setDrziNastavu(drziNastavu);
		odeljenjeRepository.save(odeljenje);
		return odeljenje; 
		}*/
	
	/*@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addRazred(@RequestBody OdeljenjeDTO odeljenje) {
		try {
		OdeljenjeEntity odelj = new OdeljenjeEntity();
		
		odelj.setBrojOdeljenja(odeljenje.getBrojOdeljenja());
		odeljenjeRepository.save(odelj);
		return new ResponseEntity<>(odeljenje, HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/
	

}

	
