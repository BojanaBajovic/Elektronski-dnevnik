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

import com.iktpreobuka.elektronskiDnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronskiDnevnik.controllers.util.RoditeljValidator;
import com.iktpreobuka.elektronskiDnevnik.dto.RoditeljDTO;
import com.iktpreobuka.elektronskiDnevnik.entities.DrziNastavuEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.OcenaEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.RoditeljEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.UcenikEntity;
import com.iktpreobuka.elektronskiDnevnik.enumerations.EKorisnikRole;
import com.iktpreobuka.elektronskiDnevnik.repositories.RoditeljRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.UcenikRepository;
import com.iktpreobuka.elektronskiDnevnik.services.LogovanjeRoditelj;

@RestController
@RequestMapping(path = "/api/roditelj")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders="*")

public class RoditeljController {

	@Autowired
	private UcenikRepository ucenikRepository;

	@Autowired
	private RoditeljRepository roditeljRepository;
	
	@Autowired RoditeljValidator roditeljValidator;
	
	@Autowired
	private LogovanjeRoditelj logovanjeRoditelj;


	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(roditeljValidator);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewRoditelj(@Valid @RequestBody RoditeljDTO noviRoditelj, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			roditeljValidator.validate(noviRoditelj, result);
		}

		RoditeljEntity roditelj = new RoditeljEntity();

		roditelj.setUsername(noviRoditelj.getUsername());
		roditelj.setPassword(noviRoditelj.getPassword());
		roditelj.setIme(noviRoditelj.getIme());
		roditelj.setPrezime(noviRoditelj.getPrezime());
		roditelj.setEmail(noviRoditelj.getEmail());
		roditelj.setKorisnikRole(EKorisnikRole.ROLE_RODITELJ);
		// roditelj.setUcenik(roditelj.getUcenik());
		roditeljRepository.save(roditelj);
		return new ResponseEntity<>(roditelj, HttpStatus.OK);
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateRoditelj(@Valid @RequestBody RoditeljDTO updateRoditelj, BindingResult result,
			@PathVariable String id) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			roditeljValidator.validate(updateRoditelj, result);
		}
		try {
			if (roditeljRepository.findById(Integer.parseInt(id)).isPresent()) {
		
				RoditeljEntity roditelj = roditeljRepository.findById(Integer.parseInt(id)).get();
		
		roditelj.setUsername(updateRoditelj.getUsername());
		roditelj.setPassword(updateRoditelj.getPassword());
		roditelj.setIme(updateRoditelj.getIme());
		roditelj.setPrezime(updateRoditelj.getPrezime());
		roditelj.setEmail(updateRoditelj.getEmail());
		return new ResponseEntity<RoditeljEntity>(roditeljRepository.save(roditelj), HttpStatus.OK);
	}
		return new ResponseEntity<RESTError>(new RESTError(1, "Roditelj sa unetim ID-jem nije pronađen."),
					HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllRoditelj() {
		try {
			return new ResponseEntity<Iterable<RoditeljEntity>>(roditeljRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getRoditeljById(@PathVariable String id) {
		try {
			if (roditeljRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RoditeljEntity>(roditeljRepository.findById(Integer.parseInt(id)).get(), HttpStatus.OK);
				} else {
			return new ResponseEntity<RESTError>(new RESTError(1, "Roditelj nije pronadjen"), HttpStatus.NOT_FOUND);
				} 
			} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> obrisiRoditeljaById(@PathVariable String id) {
		try {
			if (roditeljRepository.existsById(Integer.parseInt(id))) {
				RoditeljEntity roditelj = roditeljRepository.findById(Integer.parseInt(id)).get();

				if (!roditelj.getUcenik().isEmpty()) {
					return new ResponseEntity<RESTError>(
							new RESTError(2, "Nije dozvoljeno brisanje roditelja sa prosledjenim id !"),
							HttpStatus.BAD_REQUEST);

				} else {
					roditeljRepository.delete(roditelj);
					return new ResponseEntity<RoditeljEntity>(roditelj, HttpStatus.OK);
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Roditelj sa prosledjenim id nije pronadjen"),
					HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// na osnovu id roditelja izlistaj mi njegovu decu-ucenike
	@RequestMapping(method = RequestMethod.GET, value = "/roditelj/{id}")
	public ResponseEntity<?> findUcenikByRoditelj(@PathVariable String id) {
		
		try {
		if (roditeljRepository.existsById(Integer.parseInt(id))) {
		} return new ResponseEntity<List<UcenikEntity>>(roditeljRepository.findById(Integer.parseInt(id)).get().getUcenik(), 
				HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ovo mozda i ne treba. Izlistaj sve ucenike
	@RequestMapping(method = RequestMethod.GET, value = "/ucenici")
	public Iterable<UcenikEntity> findAllUcenik() {

		return ucenikRepository.findAll();

	}

	// na osnovu id deteta-ucenika izlistaj mi njegovog roditelja
	@RequestMapping(method = RequestMethod.GET, value = "/ucenik/{id}")
	public ResponseEntity<?> findRoditeljByUcenik(@PathVariable String id) {
	try {
		if (ucenikRepository.existsById(Integer.parseInt(id))) {
		} return new ResponseEntity<RoditeljEntity>(ucenikRepository.findById(Integer.parseInt(id)).get().getRoditelj(), 
				HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	 //Na osnovu id Ucenika izlistaj ocene
	@RequestMapping(method = RequestMethod.GET, value = "/ucenikOcene/{id}")
	public ResponseEntity<?> findOcenaByUcenik(@PathVariable String id) {
		
		try {
		if (ucenikRepository.existsById(Integer.parseInt(id))) {
		} return new ResponseEntity<List<OcenaEntity>>(ucenikRepository.findById(Integer.parseInt(id)).get().getOcena(), 
				HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/login")
	public ResponseEntity<String> login(@RequestBody RoditeljDTO roditeljDTO) {
		RoditeljEntity roditeljEntity = logovanjeRoditelj.findByUsername(roditeljDTO.getUsername());
		if (roditeljEntity == null) {
			return new ResponseEntity<>("Username doesn't exist", HttpStatus.BAD_REQUEST);
		}
		
		if (roditeljEntity.getPassword().equals(roditeljDTO.getPassword())) {
			return new ResponseEntity<>(roditeljEntity.getUsername(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Wrong password", HttpStatus.BAD_REQUEST);
		}
	}

	/*@RequestMapping(method = RequestMethod.GET, value = "/roditeljOcene/{id}")
	public ResponseEntity<?> findOceneByRoditelj(@PathVariable String id) {
		
		try {
		if (roditeljRepository.existsById(Integer.parseInt(id))) {
		 return new ResponseEntity<List<UcenikEntity>>(roditeljRepository.findById(Integer.parseInt(id)).get().getUcenik(), 
				HttpStatus.OK); }
		 else if (ucenikRepository.existsById(Integer.parseInt(id))) {
		} return new ResponseEntity<List<OcenaEntity>>(ucenikRepository.findById(Integer.parseInt(id)).get().getOcena(), 
				HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/
	
	/*
	 @RequestMapping(method = RequestMethod.POST) public ResponseEntity<?> addNewRoditelj(@RequestBody RoditeljDTO noviRoditelj) { try {
	
	 RoditeljEntity roditelj = new RoditeljEntity();
	 
	 roditelj.setUsername(noviRoditelj.getUsername());
	 roditelj.setPassword(noviRoditelj.getPassword());
	 roditelj.setIme(noviRoditelj.getIme());
	 roditelj.setPrezime(noviRoditelj.getPrezime());
	 roditelj.setEmail(noviRoditelj.getEmail());
	 roditelj.setKorisnikRole(EKorisnikRole.ROLE_RODITELJ);
	 //roditelj.setUcenik(roditelj.getUcenik());
	 roditeljRepository.save(roditelj); return new
	 ResponseEntity<RoditeljEntity>(roditelj, HttpStatus.OK); 
	 } catch (Exception e) { 
		 return new ResponseEntity<RESTError>(new RESTError (2,"Exception occured:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); 
		 }
	 }*/
	
	/*
	 @RequestMapping(method = RequestMethod.POST) public ResponseEntity<RoditeljEntity> addNewRoditelj(@RequestParam String ime, 
	 @RequestParam String prezime, @RequestParam String username, @RequestParam String password, @RequestParam String email,
	 @RequestParam UcenikEntity ucenik) {
	 
	 RoditeljEntity roditelj = new RoditeljEntity();
	 
	 roditelj.setIme(ime); roditelj.setPrezime(prezime);
	 roditelj.setUsername(username); roditelj.setPassword(password);
	 roditelj.setEmail(email); roditelj.setUcenik(roditelj.getUcenik(ucenik));
	 roditelj.setKorisnikRole(EKorisnikRole.ROLE_RODITELJ);
	 roditeljRepository.save(roditelj); return new
	 ResponseEntity<RoditeljEntity>(roditelj, HttpStatus.OK); }
	 */

	/*
	 Metoda za azuriranje podataka roditelja
	 
	 @RequestMapping(method = RequestMethod.PUT, value = "/{id}") public RoditeljEntity updateRoditelj (@PathVariable Integer id,
	 @RequestParam String ime, @RequestParam String prezime, @RequestParam String username, @RequestParam String password, 
	 @RequestParam String email, @RequestParam List<UcenikEntity> ucenik, @RequestParam EKorisnikRole korisnikRole) {
	 
	 Optional<RoditeljEntity> roditelj = roditeljRepository.findById(id);
	 
	 roditelj.get().setIme(ime); roditelj.get().setPrezime(prezime);
	 roditelj.get().setUsername(username); roditelj.get().setPassword(password);
	 roditelj.get().setEmail(email); roditelj.get().setUcenik(ucenik);
	 roditelj.get().setKorisnikRole(korisnikRole);
	 roditeljRepository.save(roditelj.get()); return roditelj.get();
	 
	 }
	 */
	/*
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateRoditelj(@PathVariable String id, @RequestBody RoditeljDTO updateRoditelj) {
	try {
		if (!roditeljRepository.findById(Integer.parseInt(id)).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Roditelj sa unetim ID-jem nije pronađen."),
					HttpStatus.NOT_FOUND);
		}
		RoditeljEntity roditelj = roditeljRepository.findById(Integer.parseInt(id)).get();

		if (updateRoditelj.getUsername() != null || !updateRoditelj.getUsername().equals(" ")
				|| !updateRoditelj.getUsername().equals("")) {
			roditelj.setUsername(updateRoditelj.getUsername());
		}
		if (updateRoditelj.getPassword() != null || !updateRoditelj.getPassword().equals(" ")
				|| !updateRoditelj.getPassword().equals("")) {
			roditelj.setPassword(updateRoditelj.getPassword());
		}

		if (updateRoditelj.getIme() != null || !updateRoditelj.getIme().equals(" ")
				|| !updateRoditelj.getIme().equals("")) {
			roditelj.setIme(updateRoditelj.getIme());
		}
		if (updateRoditelj.getPrezime() != null || !updateRoditelj.getPrezime().equals(" ")
				|| !updateRoditelj.getPrezime().equals("")) {
			roditelj.setPrezime(updateRoditelj.getPrezime());
		}
		if (updateRoditelj.getEmail() != null || !updateRoditelj.getEmail().equals(" ")
				|| !updateRoditelj.getEmail().equals("")) {
			roditelj.setEmail(updateRoditelj.getEmail());
		}

		return new ResponseEntity<RoditeljEntity>(roditeljRepository.save(roditelj), HttpStatus.OK);

	} catch (Exception e) {
		return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/
}
