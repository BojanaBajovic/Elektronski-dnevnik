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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.elektronskiDnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronskiDnevnik.controllers.util.UcenikValidator;
import com.iktpreobuka.elektronskiDnevnik.dto.UcenikDTO;
import com.iktpreobuka.elektronskiDnevnik.entities.OcenaEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.OdeljenjeEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.RoditeljEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.UcenikEntity;
import com.iktpreobuka.elektronskiDnevnik.enumerations.EKorisnikRole;
import com.iktpreobuka.elektronskiDnevnik.repositories.OdeljenjeRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.RazredRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.RoditeljRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.UcenikRepository;
import com.iktpreobuka.elektronskiDnevnik.security.Views;
import com.iktpreobuka.elektronskiDnevnik.services.LogovanjeUcenik;
import com.iktpreobuka.elektronskiDnevnik.services.UcenikDao;

@RestController 
@RequestMapping(path = "/api/ucenik")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders="*")
public class UcenikController {
	
	@Autowired
	private UcenikRepository ucenikRepository;
	
	@Autowired
	private RoditeljRepository roditeljRepository;
	
	@Autowired
	private OdeljenjeRepository odeljenjeRepository;
	
	@Autowired
	private RazredRepository razredRepository;
	
	@Autowired
	private UcenikDao ucenikdao;
	
	@Autowired
	private LogovanjeUcenik logovanjeUcenik;
	
	@Autowired UcenikValidator ucenikValidator;
	
	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(ucenikValidator);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewUcenik(@Valid @RequestBody UcenikDTO noviUcenik, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			ucenikValidator.validate(noviUcenik, result);
		}
			
			UcenikEntity ucenik = new UcenikEntity();
			
			ucenik.setUsername(noviUcenik.getUsername());
			ucenik.setPassword(noviUcenik.getPassword());
			ucenik.setIme(noviUcenik.getIme());
			ucenik.setPrezime(noviUcenik.getPrezime());
			ucenik.setKorisnikRole(EKorisnikRole.ROLE_UCENIK);
			ucenikRepository.save(ucenik);
			return new ResponseEntity<>(ucenik, HttpStatus.OK);
		} 
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateUcenik(@Valid @RequestBody UcenikDTO updateUcenik, BindingResult result,
			@PathVariable String id) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			ucenikValidator.validate(updateUcenik, result);
		}
		try {
			if (ucenikRepository.findById(Integer.parseInt(id)).isPresent()) {
		
			UcenikEntity ucenik = ucenikRepository.findById(Integer.parseInt(id)).get();
		
		ucenik.setUsername(updateUcenik.getUsername());
		ucenik.setPassword(updateUcenik.getPassword());
		ucenik.setIme(updateUcenik.getIme());
		ucenik.setPrezime(updateUcenik.getPrezime());
		return new ResponseEntity<UcenikEntity>(ucenikRepository.save(ucenik), HttpStatus.OK);
	}
		return new ResponseEntity<RESTError>(new RESTError(1, "Ucenik sa unetim ID-jem nije pronađen."),
				HttpStatus.NOT_FOUND);
	} catch (Exception e) {
		return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
	
	//Na osnovu id ucenika ubaci mu roditelja
	@RequestMapping(method = RequestMethod.PUT, value = "/ucenikR/{id}") 
	public ResponseEntity<?> addRoditeljToUcenik(@PathVariable String id, @RequestParam Integer roditelj) { 
		try {
			if (!ucenikRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError(1, "Učenik sa unetim ID-jem nije pronađen."),
						HttpStatus.NOT_FOUND);
			}
			
		Optional<UcenikEntity> ucenik = ucenikRepository.findById(Integer.parseInt(id));
		Optional<RoditeljEntity> rod = roditeljRepository.findById(roditelj);
		
		ucenik.get().setRoditelj(rod.get());
		ucenikRepository.save(ucenik.get());
		return new ResponseEntity<UcenikEntity>(ucenik.get(),HttpStatus.OK);
	
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
	//Na osnovu id ucenika ubaci mu odeljenje
	@RequestMapping(method = RequestMethod.PUT, value = "/ucenikO/{id}") 
	public ResponseEntity<?> addOdeljenjeToUcenik(@PathVariable String id, @RequestParam Integer odeljenje) { 
		try {
			if (!ucenikRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError(1, "Učenik sa unetim ID-jem nije pronađen."),
						HttpStatus.NOT_FOUND);
			}
		Optional<UcenikEntity> ucenik = ucenikRepository.findById(Integer.parseInt(id));
		Optional<OdeljenjeEntity> odelj = odeljenjeRepository.findById(odeljenje);
		
		ucenik.get().setOdeljenje(odelj.get());
		ucenikRepository.save(ucenik.get());
		return new ResponseEntity<UcenikEntity>(ucenik.get(),HttpStatus.OK);
	
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Dogodila se greška:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	//@JsonView(Views.Public.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllUcenik(){ 
		try {
			return new ResponseEntity<Iterable<UcenikEntity>>(ucenikRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Dogodila se greška:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getUcenikById(@PathVariable String id) {
		try {
			if(ucenikRepository.findById(Integer.parseInt(id)).isPresent()) {
					return new ResponseEntity<UcenikEntity>(ucenikRepository.findById(Integer.parseInt(id)).get(), HttpStatus.OK);
			}
			if(ucenikRepository.findById(Integer.parseInt(id)).isPresent()) {
			return new ResponseEntity<RoditeljEntity>(ucenikRepository.findById(Integer.parseInt(id)).get().getRoditelj(), 
					HttpStatus.OK); 
			} else {
					return new ResponseEntity<RESTError>(new RESTError(1, "Ucenik nije pronadjen"), HttpStatus.NOT_FOUND);
				}
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Dogodila se greška: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteUcenik(@PathVariable String id) {
		try {
			if (ucenikRepository.existsById(Integer.parseInt(id))) {
				UcenikEntity ucenik = ucenikRepository.findById(Integer.parseInt(id)).get();

					ucenikRepository.delete(ucenik);
					return new ResponseEntity<UcenikEntity>(ucenik, HttpStatus.OK);
				}
			return new ResponseEntity<RESTError>(new RESTError(1, "Ucenik sa prosledjenim id nije pronadjen"),
					HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//Izlistaj mi ucenike odredjenog odeljenja na osnovu id odeljenja
	@RequestMapping(method = RequestMethod.GET, value = "/odeljenje/{id}")
	public ResponseEntity<?> findUcenikByOdeljenje(@PathVariable String id) {
		try {
			if(odeljenjeRepository.findById(Integer.parseInt(id)).isPresent()) {
		return new ResponseEntity<List<UcenikEntity>> (ucenikdao.findUcenikByodeljenje(Integer.parseInt(id)), HttpStatus.OK);
			} else {
				return new ResponseEntity<RESTError>(new RESTError(1, "Odeljenje nije pronadjeno"), HttpStatus.NOT_FOUND);
			}
	} catch (Exception e) {
		return new ResponseEntity<RESTError>(new RESTError(2, "Dogodila se greška: " + e.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
	
	@RequestMapping(method = RequestMethod.GET, value = "/odeljenje/{id}/razred/{brojRazreda}")
	public ResponseEntity<?> findUcenikByOdeljenjeIRazred(@PathVariable String id, @PathVariable String brojRazreda) {
		try {
			if(odeljenjeRepository.findById(Integer.parseInt(id)).isPresent() 
					&& razredRepository.findById(Integer.parseInt(brojRazreda)).isPresent()) {
				return new ResponseEntity<List<UcenikEntity>> (ucenikdao.findUcenikByodeljenjeIRazred(Integer.parseInt(id),
						Integer.parseInt(brojRazreda)), HttpStatus.OK);
			} else {
				return new ResponseEntity<RESTError>(new RESTError(1, "Odeljenje i razred nisu pronadjeni"), HttpStatus.NOT_FOUND);
			}
	} catch (Exception e) {
		return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
	
	@RequestMapping(method = RequestMethod.GET, value = ("/byNameFirstLetter/{letter}"))
	public ResponseEntity<?> vratiPoPrvomSlovu(@PathVariable char letter){
	try {
		return new ResponseEntity<List<UcenikEntity>>(ucenikRepository.findByImeStartingWith(letter),HttpStatus.OK);
	}catch (Exception e) {
		return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//izlista sve djake iz svih razreda po abecedi
	@RequestMapping(method = RequestMethod.GET, value="/by-prezime")
	public ResponseEntity<?> getSveUcenikeByPrezimeAsc() {
		try {
			return new ResponseEntity<List<UcenikEntity>>(ucenikRepository.findAllByOrderByPrezimeAsc(),HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	//Izlistaj ucenike iz odredjenog odeljenja po abecedi
	@RequestMapping(method = RequestMethod.GET, value = "/odeljenjeSort/{id}")
	public ResponseEntity<?> findUcenikByOdeljenjeSort(@PathVariable String id) {
	try {
		if(odeljenjeRepository.findById(Integer.parseInt(id)).isPresent()) {
	return new ResponseEntity<List<UcenikEntity>> (ucenikdao.findAllByOrderByPrezimeAsc(Integer.parseInt(id)), HttpStatus.OK);
		} else {
			return new ResponseEntity<RESTError>(new RESTError(1, "Odeljenje nije pronadjeno"), HttpStatus.NOT_FOUND);
		}
	} catch (Exception e) {
	return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
			HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
	// Izlistaj roditelja na osnovu ID ucenika
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
	
	// na osnovu id ucenika izlistaj mi njegove ocene
		@RequestMapping(method = RequestMethod.GET, value = "/ucenikOcene/{id}")
		public ResponseEntity<?> findOceneByUcenik(@PathVariable String id) {
			
			try {
			if (ucenikRepository.existsById(Integer.parseInt(id))) {
			} return new ResponseEntity<List<OcenaEntity>>(ucenikRepository.findById(Integer.parseInt(id)).get().getOcena(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@RequestMapping(method=RequestMethod.POST, value="/login")
		public ResponseEntity<String> login(@RequestBody UcenikDTO ucenikDTO) {
			UcenikEntity ucenikEntity = logovanjeUcenik.findByUsername(ucenikDTO.getUsername());
			if (ucenikEntity == null) {
				return new ResponseEntity<>("Username doesn't exist", HttpStatus.BAD_REQUEST);
			}
			
			if (ucenikEntity.getPassword().equals(ucenikDTO.getPassword())) {
				return new ResponseEntity<>(ucenikEntity.getUsername(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Wrong password", HttpStatus.BAD_REQUEST);
			}
		}
		
		
	/*@RequestMapping(method = RequestMethod.GET, value="/byName/{firstLetter}")
	public List<String> getNames(@PathVariable String firstLetter) {
		
		List<String> name=new ArrayList<String>();
		List<UcenikEntity> ucenici = (List<UcenikEntity>) ucenikRepository.findAll();
			for(UcenikEntity u : ucenici)
				if (u.getIme().substring(0,1).equalsIgnoreCase(firstLetter))
				name.add(u.getIme());
				return name;
		}*/
	/*Ovo vec imam
	@RequestMapping(method = RequestMethod.GET, value = "/odeljenje/sort/{id}")
	public List<UcenikEntity> addUceniktoOdeljenjeSort(@PathVariable Integer id) {
	return ucenikdao.findUcenikByodeljenjeSort(id);
	}*/
	
	/*@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateUcenik(@PathVariable Integer id, @RequestBody UcenikEntity ucenik) {
		try {
			ucenikRepository.save(ucenik);
			return new ResponseEntity<UcenikEntity>(ucenik, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError (2, "Exception occured:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/
	
	
	/*@RequestMapping(method = RequestMethod.PUT, value = "/{id}") 
	public UcenikEntity updateUcenik (@PathVariable Integer id, @RequestBody UcenikEntity ucenik) { 
		
		if (ucenikRepository.existsById(id)) {
			UcenikEntity ucenikEntity = ucenikRepository.findById(id).get();
				if (ucenik.getIme() != null) {
					ucenikEntity.setIme(ucenik.getIme());
				}
				if (ucenik.getPrezime() != null) {
					ucenikEntity.setPrezime(ucenik.getPrezime());
				}
				if (ucenik.getUsername() != null) {
					ucenikEntity.setUsername(ucenik.getUsername());
				}
				if (ucenik.getPassword() != null) {
					ucenikEntity.setPassword(ucenik.getPassword());
				}
				if (ucenik.getRoditelj() != null) {
					ucenikEntity.setRoditelj(ucenik.getRoditelj());
				}
				if (ucenik.getOdeljenje() != null) {
					ucenikEntity.setOdeljenje(ucenik.getOdeljenje());
				}
				if (ucenik.getKorisnikRole() != null) {
					ucenikEntity.setKorisnikRole(ucenik.getKorisnikRole());
				}
				return ucenikRepository.save(ucenikEntity);
			}
		return null;
		}*/
	
	/* Metoda za azuriranje podataka ucenika
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}") 
	public UcenikEntity updateUcenik (@PathVariable Integer id,@RequestParam String ime, @RequestParam String prezime, 
			@RequestParam String username, @RequestParam String password, @RequestParam RoditeljEntity roditelj,
			@RequestParam OdeljenjeEntity odeljenje, @RequestParam EKorisnikRole korisnikRole) { 
		
		Optional<UcenikEntity> ucenik = ucenikRepository.findById(id);
		
		ucenik.get().setIme(ime);
		ucenik.get().setPrezime(prezime);
		ucenik.get().setUsername(username);
		ucenik.get().setPassword(password);
		ucenik.get().setRoditelj(roditelj);
		ucenik.get().setOdeljenje(odeljenje);
		ucenik.get().setKorisnikRole(korisnikRole);
		ucenikRepository.save(ucenik.get());
		return ucenik.get();
	
		}*/
	//RADI
		/*@RequestMapping(method = RequestMethod.POST)
		public UcenikEntity createUcenik(@RequestParam String ime, @RequestParam String prezime, 
				@RequestParam String username, @RequestParam String password, @RequestParam RoditeljEntity roditelj,
				@RequestParam OdeljenjeEntity odeljenje) {
			
			UcenikEntity ucenik = new UcenikEntity();
			
			ucenik.setIme(ime);
			ucenik.setPrezime(prezime);
			ucenik.setUsername(username);
			ucenik.setPassword(password);
			ucenik.setRoditelj(roditelj);
			ucenik.setOdeljenje(odeljenje);
			roditelj.setKorisnikRole(EKorisnikRole.ROLE_UCENIK);
			ucenikRepository.save(ucenik);

			return ucenik;
		
		}*/
	/*Na osnovu id ucenika ubaci mu roditelja RADI
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/roditelj") 
	public ResponseEntity<?> addRoditeljToUcenik(@PathVariable Integer id, @RequestParam Integer roditelj) { 
		
		if (!ucenikRepository.findById(id).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Učenik sa unetim ID-jem nije pronađen."),
					HttpStatus.NOT_FOUND);
		}
		
		
		Optional<UcenikEntity> ucenik = ucenikRepository.findById(id);
		Optional<RoditeljEntity> rod = roditeljRepository.findById(roditelj);
		
		ucenik.get().setRoditelj(rod.get());
		
		return new ResponseEntity<UcenikEntity>(ucenikRepository.save(ucenik.get()), HttpStatus.OK);
	
		}*/
	/*@RequestMapping(method = RequestMethod.POST, value = "/{roditelj}/roditelj")
	public ResponseEntity<?> addNewUcenik(@Valid @RequestBody UcenikDTO noviUcenik,@PathVariable Integer roditelj, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			ucenikValidator.validate(noviUcenik, result);
		}
			
			UcenikEntity ucenik = new UcenikEntity();
			RoditeljEntity rod = roditeljRepository.findById(noviUcenik.getRoditelj()).get();
			//OdeljenjeEntity odeljenje = odeljenjeRepository.findById(odeljenjeId).get();
			
			ucenik.setUsername(noviUcenik.getUsername());
			ucenik.setPassword(noviUcenik.getPassword());
			ucenik.setIme(noviUcenik.getIme());
			ucenik.setPrezime(noviUcenik.getPrezime());
			ucenik.setKorisnikRole(EKorisnikRole.ROLE_UCENIK);
			ucenik.setRoditelj(rod);
			ucenikRepository.save(ucenik);
			return new ResponseEntity<>(noviUcenik, HttpStatus.OK);
		} 
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}*/
	
	/*@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateUcenik(@PathVariable String id, @RequestBody UcenikDTO updateUcenik) {
		try {
		if (!ucenikRepository.findById(Integer.parseInt(id)).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Učenik sa unetim ID-jem nije pronađen."),
					HttpStatus.NOT_FOUND);
		}
		UcenikEntity ucenik = ucenikRepository.findById(Integer.parseInt(id)).get();

		if (updateUcenik.getUsername() != null || !updateUcenik.getUsername().equals(" ")
				|| !updateUcenik.getUsername().equals("")) {
			ucenik.setUsername(updateUcenik.getUsername());
		}
		if (updateUcenik.getPassword() != null || !updateUcenik.getPassword().equals(" ")
				|| !updateUcenik.getPassword().equals("")) {
			ucenik.setPassword(updateUcenik.getPassword());
		}

		if (updateUcenik.getIme() != null || !updateUcenik.getIme().equals(" ")
				|| !updateUcenik.getIme().equals("")) {
			ucenik.setIme(updateUcenik.getIme());
		}
		if (updateUcenik.getPrezime() != null || !updateUcenik.getPrezime().equals(" ")
				|| !updateUcenik.getPrezime().equals("")) {
			ucenik.setPrezime(updateUcenik.getPrezime());
		}
		
		return new ResponseEntity<UcenikEntity>(ucenikRepository.save(ucenik), HttpStatus.OK);

	} catch (Exception e) {
		return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/
}