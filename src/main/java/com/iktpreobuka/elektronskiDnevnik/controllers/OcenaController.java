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
import com.iktpreobuka.elektronskiDnevnik.dto.OcenaDTO;
import com.iktpreobuka.elektronskiDnevnik.entities.DrziNastavuEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.OcenaEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.UcenikEntity;
import com.iktpreobuka.elektronskiDnevnik.models.EmailObject;
import com.iktpreobuka.elektronskiDnevnik.repositories.DrziNastavuRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.OcenaRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.UcenikRepository;
import com.iktpreobuka.elektronskiDnevnik.services.EmailService;
import com.iktpreobuka.elektronskiDnevnik.services.OcenaDao;

@RestController
@RequestMapping(path = "/api/ocena")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders="*")

public class OcenaController {

	@Autowired
	private UcenikRepository ucenikRepository;
	
	@Autowired
	private OcenaRepository ocenaRepository;
	
	@Autowired
	private DrziNastavuRepository drziNastavuRepository;
	
	@Autowired 
	private EmailService emailService; //posaljiMail
	
	@Autowired
	private OcenaDao ocenadao;
	
	/*
	@Autowired OcenaValidator ocenaValidator;
	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(ocenaValidator);
	}*/
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewOcena(@Valid @RequestBody OcenaDTO novaOcena, BindingResult result) {
		if(result.hasErrors()) { 
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST); 
		} 
		if (!drziNastavuRepository.findById(novaOcena.getIdDrziNastavu()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Drzi nastavu nije pronadjen"), HttpStatus.NOT_FOUND);
		}
		DrziNastavuEntity drziNastavu = drziNastavuRepository.findById(novaOcena.getIdDrziNastavu()).get();
		
		
		if (!ucenikRepository.findById(novaOcena.getIdUcenik()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Ucenik nije pronadjen"), HttpStatus.NOT_FOUND);
		} 
		UcenikEntity ucenik = ucenikRepository.findById(novaOcena.getIdUcenik()).get();

		OcenaEntity ocena = new OcenaEntity();
		
		ocena.setVrednostOcene(novaOcena.getVrednostOcene());
		ocena.setDatum(novaOcena.getDatum());
		ocena.setPolugodiste(novaOcena.getPolugodiste());
		ocena.setDrziNastavu(drziNastavu);
		ocena.setUcenik(ucenik);
		
		ocenaRepository.save(ocena);
		EmailObject mail = new EmailObject(ucenik.getRoditelj().getEmail(), "Nova Ocena", text(ocena));
		emailService.sendSimpleMessage(mail);
		
		return new ResponseEntity<>(ocena, HttpStatus.OK);
	} 
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	private String text(OcenaEntity ocena) {
		return "Učenik/ca" + " " + ocena.getUcenik().getIme() + " " + ocena.getUcenik().getPrezime() + " " + "je dobio/dobila ocenu" + " " + ocena.getVrednostOcene() 
		+ " " + "iz predmeta" + " " + ocena.getDrziNastavu().getPredaje().getPredmet().getNazivPredmeta() + " " + "kod nastavnika" + " " +
				ocena.getDrziNastavu().getPredaje().getNastavnik().getIme() + " " + ocena.getDrziNastavu().getPredaje().getNastavnik().getPrezime();
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> addNewOcena(@Valid @RequestBody OcenaDTO updateOcena, BindingResult result,
			@PathVariable String id) {
		if(result.hasErrors()) { 
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST); 
		}
		if (!ocenaRepository.findById(Integer.parseInt(id)).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Ocena nije pronadjena"), HttpStatus.NOT_FOUND);
		}
		
		if (!drziNastavuRepository.findById(updateOcena.getIdDrziNastavu()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Drzi nastavu nije pronadjen"), HttpStatus.NOT_FOUND);
		}
		
		if (!ucenikRepository.findById(updateOcena.getIdUcenik()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Ucenik nije pronadjen"), HttpStatus.NOT_FOUND);
		} 
		OcenaEntity ocena1 = ocenaRepository.findById(Integer.parseInt(id)).get();
		DrziNastavuEntity drziNastavu = drziNastavuRepository.findById(updateOcena.getIdDrziNastavu()).get();
		UcenikEntity ucenik = ucenikRepository.findById(updateOcena.getIdUcenik()).get();
		
		ocena1.setVrednostOcene(updateOcena.getVrednostOcene());
		ocena1.setDatum(updateOcena.getDatum());
		ocena1.setPolugodiste(updateOcena.getPolugodiste());
		ocena1.setDrziNastavu(drziNastavu);
		ocena1.setUcenik(ucenik);
		
		ocenaRepository.save(ocena1);
		EmailObject mail = new EmailObject(ucenik.getRoditelj().getEmail(), "Promena ocene", text1(ocena1));
		emailService.sendSimpleMessage(mail);
		
		return new ResponseEntity<OcenaEntity>(ocena1, HttpStatus.OK);
		
	} 
	private String text1(OcenaEntity ocena1) {
		return "Učeniku/ci" + " " + ocena1.getUcenik().getIme() + " " + ocena1.getUcenik().getPrezime() + ", dana: " + ocena1.getDatum()
	+ " je promenjena ocena na:" + " " + ocena1.getVrednostOcene() 
		+ " " + "iz predmeta" + " " + ocena1.getDrziNastavu().getPredaje().getPredmet().getNazivPredmeta() + " " + "kod nastavnika" + " " +
				ocena1.getDrziNastavu().getPredaje().getNastavnik().getIme() + " " + ocena1.getDrziNastavu().getPredaje().getNastavnik().getPrezime();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllOcena(){ 
		try {
			return new ResponseEntity<Iterable<OcenaEntity>>(ocenaRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getOcenaById(@PathVariable String id) {
		try {
			if(ocenaRepository.findById(Integer.parseInt(id)).isPresent()) {
					return new ResponseEntity<OcenaEntity>(ocenaRepository.findById(Integer.parseInt(id)).get(), HttpStatus.OK);
			} else {
					return new ResponseEntity<RESTError>(new RESTError(1, "Ocena nije pronadjena"), HttpStatus.NOT_FOUND);
				}
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Dogodila se greška: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteOcena(@PathVariable String id) {
		try {
			if (ocenaRepository.existsById(Integer.parseInt(id))) {
				OcenaEntity ocena = ocenaRepository.findById(Integer.parseInt(id)).get();

					ocenaRepository.delete(ocena);
					return new ResponseEntity<OcenaEntity>(ocena, HttpStatus.OK);
				}
			return new ResponseEntity<RESTError>(new RESTError(1, "Ocena sa prosledjenim id nije pronadjen"),
					HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Izlistaj mi ocene na osnovu id ucenika
		@RequestMapping(method = RequestMethod.GET, value = "/ucenik/{id}")
		public ResponseEntity<?> findOcenaByUcenik(@PathVariable String id) {
			try {
				if(ucenikRepository.findById(Integer.parseInt(id)).isPresent()) {
			return new ResponseEntity<List<OcenaEntity>> (ocenadao.findOcenaByUcenik(Integer.parseInt(id)), HttpStatus.OK);
				} else {
					return new ResponseEntity<RESTError>(new RESTError(1, "Ocena nije pronadjena"), HttpStatus.NOT_FOUND);
				}
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
		// na osnovu id drziNastavu izlistaj ocene
		@RequestMapping(method = RequestMethod.GET, value = "/drziNastavu/{id}")
		public ResponseEntity<?> findOceneByDrziNastavu(@PathVariable String id) {
							
		try {
			if (drziNastavuRepository.existsById(Integer.parseInt(id))) {
				} return new ResponseEntity<List<OcenaEntity>>(drziNastavuRepository.findById(Integer.parseInt(id)).get().getOcena(),
					HttpStatus.OK);
				} catch (Exception e) {
					return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
	
		
	
	/*
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> addNewOcena(@Valid @RequestBody OcenaDTO updateOcena, BindingResult result,
			@PathVariable String id) {
		if(result.hasErrors()) { 
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST); 
		}

		OcenaEntity ocena1 = ocenaRepository.findById(Integer.parseInt(id)).get();
		UcenikEntity ucenik = ucenikRepository.findById(updateOcena.getIdUcenik()).get();

		if (ocena1 == null || updateOcena == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Ocena with provided ID not found."),
					HttpStatus.NOT_FOUND);
		}
		if (ucenikRepository.existsById(updateOcena.getIdUcenik())) {

		if (updateOcena.getVrednostOcene() != null) {
			ocena1.setVrednostOcene(updateOcena.getVrednostOcene());
		}
		if (updateOcena.getDatum() != null) {
			ocena1.setDatum(updateOcena.getDatum());
		}}
		EmailObject mail = new EmailObject(ucenik.getRoditelj().getEmail(), "Promena ocene", text1(ocena1));
		emailService.sendSimpleMessage(mail);
		return new ResponseEntity<OcenaEntity>(ocenaRepository.save(ocena1), HttpStatus.OK);
	}
		
	private String text1(OcenaEntity ocena1) {
		return "Učeniku/ci" + " " + ocena1.getUcenik().getIme() + " " + ocena1.getUcenik().getPrezime() + ", dana: " + ocena1.getDatum()
	+ " je promenjena ocena na:" + " " + ocena1.getVrednostOcene() 
		+ " " + "iz predmeta" + " " + ocena1.getDrziNastavu().getPredaje().getPredmet().getNazivPredmeta() + " " + "kod nastavnika" + " " +
				ocena1.getDrziNastavu().getPredaje().getNastavnik().getIme() + " " + ocena1.getDrziNastavu().getPredaje().getNastavnik().getPrezime();
	}*/
	
	
	/*@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewOcena(@RequestParam Integer ucenikId, @RequestParam Integer drziNastavuId, @RequestBody OcenaDTO ocena) {
	try {
	if (ucenikRepository.findById(ucenikId).isPresent()) {
	if (drziNastavuRepository.findById(drziNastavuId).isPresent()) {
	if ((ocena.getVrednostOcene() >= 1) && (ocena.getVrednostOcene() <= 5)) {
	Optional<DrziNastavuEntity> drziNastavu = drziNastavuRepository.findById(drziNastavuId);
	Optional<UcenikEntity> ucenik = ucenikRepository.findById(ucenikId);

	OcenaEntity oc = new OcenaEntity();
	oc.setDrziNastavu(drziNastavu.get());
	oc.setUcenik(ucenik.get());
	oc.setVrednostOcene(ocena.getVrednostOcene());
	oc.setKonacnaOcena(ocena.getKonacnaOcena());
	//oc.setDatum(ocena.getDatum());
	oc.setPolugodiste(ocena.getPolugodiste());
	//oc.setDeleted(false);
	ocenaRepository.save(oc);
	return new ResponseEntity<OcenaEntity>(oc, HttpStatus.OK);
	} else
	return new ResponseEntity<RESTError>(new RESTError(1, "Ocena mora biti izmedju 1 i 5"), HttpStatus.BAD_REQUEST);
	}else
	return new ResponseEntity<RESTError>(new RESTError(2, "Nastavnik ne postoji u bazi"), HttpStatus.NOT_FOUND);
	}else
	return new ResponseEntity<RESTError>(new RESTError(3, "Ucenik ne postoji u bazi"), HttpStatus.NOT_FOUND);
	} catch (Exception e) {
		return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	} 
}
*/
	}