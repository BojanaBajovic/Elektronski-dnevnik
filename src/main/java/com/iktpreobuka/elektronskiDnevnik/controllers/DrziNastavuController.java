package com.iktpreobuka.elektronskiDnevnik.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskiDnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronskiDnevnik.dto.DrziNastavuDTO;

import com.iktpreobuka.elektronskiDnevnik.entities.DrziNastavuEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.OcenaEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.OdeljenjeEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.PredajeEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.UcenikEntity;
import com.iktpreobuka.elektronskiDnevnik.repositories.DrziNastavuRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.OdeljenjeRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.PredajeRepository;
import com.iktpreobuka.elektronskiDnevnik.services.DrziNastavuDao;

@RestController
@RequestMapping(path = "/api/drziNastavu")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders="*")

public class DrziNastavuController {

	@Autowired
	private DrziNastavuRepository drziNastavuRepository;

	@Autowired
	private PredajeRepository predajeRepository;

	@Autowired
	private OdeljenjeRepository odeljenjeRepository;

	@Autowired
	private DrziNastavuDao drziNastavudao;

	@RequestMapping(method = RequestMethod.POST, value = "/predaje/{predajeId}/odeljenje/{odeljenjeId}")
	public ResponseEntity<?> addNewDrziNastavu(@PathVariable Integer predajeId, @PathVariable Integer odeljenjeId) {

		DrziNastavuEntity drziNastavu = new DrziNastavuEntity();
		if (!predajeRepository.findById(predajeId).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Predaje ID not found"), HttpStatus.NOT_FOUND);
		}

		if (!odeljenjeRepository.findById(odeljenjeId).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Odeljenje ID not found."), HttpStatus.NOT_FOUND);
		}

		PredajeEntity predaje = predajeRepository.findById(predajeId).get();
		OdeljenjeEntity odeljenje = odeljenjeRepository.findById(odeljenjeId).get();

		drziNastavu.setOdeljenje(odeljenje);
		drziNastavu.setPredaje(predaje);

		// drziNastavudao.findDrziNastavuByPredajeOdeljnje(predajeId, odeljenjeId);
		// drziNastavudao.findDrziNastavuByPredajeOdeljnje(predajeId, odeljenjeId);
		return new ResponseEntity<DrziNastavuEntity>(drziNastavuRepository.save(drziNastavu), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateDrziNastavu(@PathVariable Integer id, @RequestBody DrziNastavuDTO updateDrziNastavu) {

		if (!drziNastavuRepository.findById(id).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Drzi nastavu sa unetim ID-jem nije pronađen."),
					HttpStatus.NOT_FOUND);
		}
		DrziNastavuEntity drziNastavu = drziNastavuRepository.findById(id).get();
		
		if (!predajeRepository.findById(updateDrziNastavu.getIdPredaje()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Predaje nije pronadjen"), HttpStatus.NOT_FOUND);
		}
		
		if (!odeljenjeRepository.findById(updateDrziNastavu.getIdOdeljenje()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Odeljenje nije pronadjen"), HttpStatus.NOT_FOUND);
		}
		PredajeEntity predaje = predajeRepository.findById(updateDrziNastavu.getIdPredaje()).get();
		OdeljenjeEntity odeljenje = odeljenjeRepository.findById(updateDrziNastavu.getIdOdeljenje()).get();

		drziNastavu.setOdeljenje(odeljenje);
		drziNastavu.setPredaje(predaje);
		
		return new ResponseEntity<DrziNastavuEntity>(drziNastavuRepository.save(drziNastavu), HttpStatus.OK);

	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAlldrziNastavu(){ 
		try {
			return new ResponseEntity<Iterable<DrziNastavuEntity>>(drziNastavuRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getDrziNastavuById(@PathVariable String id) {
		try {
			if(drziNastavuRepository.findById(Integer.parseInt(id)).isPresent()) {
					return new ResponseEntity<DrziNastavuEntity>(drziNastavuRepository.findById(Integer.parseInt(id)).get(), HttpStatus.OK);
				} else {
					return new ResponseEntity<RESTError>(new RESTError(1, "Nastavnik drzi nastavu nije pronadjen"), HttpStatus.NOT_FOUND);
				}
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Na osnovu id odeljenja izlistaj mi Drzi Nastavu-izlista se sve sto se nalazi u Drzi Nastavu
	@RequestMapping(method = RequestMethod.GET, value = "/odeljenje/{id}")
	public ResponseEntity<?> getDrziNastavuByOdeljenje(@PathVariable Integer id) {
		try {
			if (odeljenjeRepository.findById(id).isPresent()) {
				return new ResponseEntity<List<DrziNastavuEntity>>(drziNastavudao.findDrziNastavuByOdeljenje(id),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<RESTError>(new RESTError(1, "Drzi Nastavu nije pronadjen"), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Na osnovu id predaje izlistaj mi Drzi Nastavu-izlista se sve sto se nalazi
	// u Drzi Nastavu
	@RequestMapping(method = RequestMethod.GET, value = "/predaje/{id}")
	public ResponseEntity<?> getDrziNastavuByPredaje(@PathVariable Integer id) {
		try {
			if (predajeRepository.findById(id).isPresent()) {
				return new ResponseEntity<List<DrziNastavuEntity>>(drziNastavudao.findDrziNastavuByPredaje(id),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<RESTError>(new RESTError(1, "Predaje nije pronadjen"), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// na osnovu id drziNastavu izlistaj ocene
			@RequestMapping(method = RequestMethod.GET, value = "/drziNastavu/{id}")
			public ResponseEntity<?> findOceneByUcenik(@PathVariable String id) {
				
				try {
				if (drziNastavuRepository.existsById(Integer.parseInt(id))) {
				} return new ResponseEntity<List<OcenaEntity>>(drziNastavuRepository.findById(Integer.parseInt(id)).get().getOcena(),
						HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			
			@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
			public ResponseEntity<?> deleteDrziNastavu(@PathVariable String id) {
				try {
					if (drziNastavuRepository.existsById(Integer.parseInt(id))) {
						DrziNastavuEntity drziNastavu = drziNastavuRepository.findById(Integer.parseInt(id)).get();

						drziNastavuRepository.delete(drziNastavu);
							return new ResponseEntity<DrziNastavuEntity>(drziNastavu, HttpStatus.OK);
						}
					return new ResponseEntity<RESTError>(new RESTError(1, "Drzi nastavu sa prosledjenim id nije pronadjen"),
							HttpStatus.NOT_FOUND);

				} catch (Exception e) {
					return new ResponseEntity<RESTError>(new RESTError(3, "Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}

	/*
	 * @RequestMapping(method = RequestMethod.POST) public ResponseEntity<?>
	 * createNewDrziNastavu(@RequestBody DrziNastavuDTO novi) {
	 * 
	 * 
	 * if (!drziNastavuRepository.findById(id).isPresent()) { return new
	 * ResponseEntity<RESTError>(new RESTError(1,
	 * "Drzi Nastavu sa unetim ID-jem nije pronađen."), HttpStatus.NOT_FOUND); }
	 * DrziNastavuEntity drziNastavu = new DrziNastavuEntity();
	 * 
	 * if (novi.getIdOdeljenje() != null) {
	 * drziNastavu.setOdeljenje(novi.getIdOdeljenje()); } if (novi.getIdPredaje() !=
	 * null) { drziNastavu.setPredaje(novi.getIdPredaje()); }
	 * 
	 * return new
	 * ResponseEntity<DrziNastavuEntity>(drziNastavuRepository.save(drziNastavu),
	 * HttpStatus.OK);
	 * 
	 * }
	 */

	/*
	 * @RequestMapping(method = RequestMethod.POST) public ResponseEntity<?>
	 * createNewDrziNastavu(@RequestBody DrziNastavuDTO novi) { DrziNastavuEntity
	 * drziNastavu = new DrziNastavuEntity();
	 * 
	 * drziNastavu.setId(id); drziNastavu.setOdeljenje(novi.getIdOdeljenje());
	 * drziNastavu.setPredaje(novi.getIdPredaje());
	 * drziNastavuRepository.save(drziNastavu);
	 * 
	 * return new ResponseEntity<>(novi, HttpStatus.OK); }
	 */
	
	/*
	 * @RequestMapping(method = RequestMethod.POST) public DrziNastavuEntity
	 * addNewNastava(@RequestBody DrziNastavuEntity newUser) { if (newUser == null)
	 * { return null; }
	 * 
	 * if (newUser.getOcena() == null || newUser.getOdeljenje() == null ||
	 * newUser.getPredaje() == null) { return null; }
	 * 
	 * return drziNastavuRepository.save(newUser); }
	 */

	/*
	 * @RequestMapping(method = RequestMethod.POST) public ResponseEntity<?>
	 * addNewDrziNastavu(@RequestBody DrziNastavuEntity novi) {
	 * 
	 * return new ResponseEntity<DrziNastavuEntity>
	 * (drziNastavuRepository.save(novi), HttpStatus.OK); }
	 */

	/*
	 * @RequestMapping(method = RequestMethod.POST) public ResponseEntity<?>
	 * addNewDrziNastavu(@RequestBody DrziNastavuDTO novi) { try {
	 * //if(novi.getIdPredaje() == null || { DrziNastavuEntity drziNastavu = new
	 * DrziNastavuEntity();
	 * 
	 * drziNastavu.setOdeljenje(novi.getIdOdeljenje());
	 * drziNastavu.setPredaje(novi.getIdPredaje());
	 * 
	 * drziNastavuRepository.save(drziNastavu); return new ResponseEntity<>(novi,
	 * HttpStatus.OK);
	 * 
	 * } catch (Exception e) { return new ResponseEntity<RESTError>(new RESTError(2,
	 * "Exception occured:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); }
	 * }
	 */
	/*
	 * @RequestMapping(method = RequestMethod.POST) public ResponseEntity<?>
	 * addNewDrziNastavu(@RequestBody DrziNastavuDTO novi) {
	 * 
	 * if (!drziNastavuRepository.findById().isPresent()) { return new
	 * ResponseEntity<RESTError>(new RESTError(1, "ID nije pronađen."),
	 * HttpStatus.NOT_FOUND); } DrziNastavuEntity drziNastavu = new
	 * DrziNastavuEntity();
	 * 
	 * if (!odeljenjeRepository.findById(novi.getIdOdeljenje()).isPresent()) {
	 * return new ResponseEntity<RESTError>(new RESTError(0,
	 * "Offer with provided ID not found"), HttpStatus.NOT_FOUND); }
	 * 
	 * if (!predajeRepository.findById(novi.getIdPredaje()).isPresent()) { return
	 * new ResponseEntity<RESTError>(new RESTError(0,
	 * "User with provided ID not found."), HttpStatus.NOT_FOUND); }
	 * 
	 * OdeljenjeEntity odeljenje =
	 * odeljenjeRepository.findById(novi.getIdOdeljenje()).get(); PredajeEntity
	 * predaje = predajeRepository.findById(novi.getIdPredaje()).get();
	 * 
	 * novi.setIdOdeljenje(odeljenje); novi.setIdPredaje(predaje);
	 * 
	 * return new
	 * ResponseEntity<DrziNastavuEntity>(drziNastavuRepository.save(novi),
	 * HttpStatus.OK); }
	 */

	/*
	 * private String generateErrorMessage(BindingResult result) { String msg = " ";
	 * for (ObjectError error : result.getAllErrors()) { msg +=
	 * error.getDefaultMessage(); msg += " "; }
	 * 
	 * return msg; }
	 * 
	 * @RequestMapping(method = RequestMethod.POST, value =
	 * "/{predajeId}/{odeljenjeId}") public ResponseEntity<?>
	 * addDrziNastavu(@Valid @RequestBody DrziNastavuDTO newDrziNastavu,
	 * BindingResult result, @PathVariable Integer predajeId,
	 * 
	 * @PathVariable Integer odeljenjeId) { if(result.hasErrors()) { //return new
	 * ResponseEntity<String>(this.generateErrorMessage(result),HttpStatus.
	 * BAD_REQUEST); // može i da se iskoristi RESTError return new
	 * ResponseEntity<RESTError>(new RESTError(0,
	 * this.generateErrorMessage(result)), HttpStatus.BAD_REQUEST); }
	 * DrziNastavuEntity drziNastavu = new DrziNastavuEntity();
	 * 
	 * PredajeEntity predaje = predajeRepository.findById(predajeId).get();
	 * OdeljenjeEntity odeljenje = odeljenjeRepository.findById(odeljenjeId).get();
	 * 
	 * if (predaje == null) { return new ResponseEntity<RESTError>(new RESTError(1,
	 * "Category with provided ID not found."), HttpStatus.NOT_FOUND); }
	 * 
	 * if (odeljenje == null) { return new ResponseEntity<RESTError>(new
	 * RESTError(2, "User with provided ID not found."), HttpStatus.NOT_FOUND); }
	 * 
	 * newDrziNastavu.setPredajeId(predajeId);
	 * newDrziNastavu.setOdeljenjeId(odeljenjeId);
	 * 
	 * //drziNastavu.setOdeljenje(newDrziNastavu.getIdOdeljenje());
	 * //drziNastavu.setPredaje(newDrziNastavu.getIdPredaje());
	 * 
	 * 
	 * 
	 * return new
	 * ResponseEntity<DrziNastavuEntity>(drziNastavuRepository.save(newDrziNastavu),
	 * HttpStatus.OK); }
	 */

	/*
	 * @RequestMapping(method = RequestMethod.POST) public DrziNastavuEntity
	 * addNewUser(@RequestBody DrziNastavuEntity newDn) { if (newDn == null) {
	 * return null; }
	 * 
	 * if (newDn.getOcena() == null || newDn.getOdeljenje() == null ||
	 * newDn.getPredaje() == null) { return null; }
	 * 
	 * return drziNastavuRepository.save(newDn); }
	 */

	/*
	 * @RequestMapping(method = RequestMethod.POST) public ResponseEntity<?>
	 * addNewUcenik(@RequestBody DrziNastavuDTO noviDrziNastavu) {
	 * 
	 * DrziNastavuEntity drziNastavu = new DrziNastavuEntity();
	 * 
	 * OdeljenjeEntity odeljenje = new OdeljenjeEntity(); PredajeEntity predaje =
	 * new PredajeEntity();
	 * 
	 * drziNastavu.setOdeljenje(odeljenje.getId(odeljenjeId);
	 * drziNastavu.setPredaje(predaje.getId());
	 * drziNastavu.setOcena(noviDrziNastavu.getOcena());
	 * drziNastavuRepository.save(drziNastavu); return new
	 * ResponseEntity<>(drziNastavu, HttpStatus.OK); }
	 */
	/*
	 * @RequestMapping(method = RequestMethod.POST) public
	 * ResponseEntity<DrziNastavuEntity> createDrziNastavu(@RequestParam Integer
	 * id, @RequestParam OcenaEntity ocena,
	 * 
	 * @RequestParam OdeljenjeEntity odeljenje, @RequestParam PredajeEntity predaje)
	 * {
	 * 
	 * DrziNastavuEntity drziNastavu = new DrziNastavuEntity();
	 * 
	 * drziNastavu.setId(id); drziNastavu.setOcena(drziNastavu.getOcena());
	 * drziNastavu.setOdeljenje(odeljenje); drziNastavu.setPredaje(predaje);
	 * drziNastavuRepository.save(drziNastavu); return new
	 * ResponseEntity<DrziNastavuEntity>(drziNastavu, HttpStatus.OK); }
	 */
	
	/*
	 * @RequestMapping(method = RequestMethod.GET, value =
	 * "/predaje/{predajeId}/odeljenje/{odeljenjeId}") public ResponseEntity<?>
	 * findDrziNastavuByPredajeOdeljnje(@PathVariable Integer
	 * predajeId, @PathVariable Integer odeljenjeId) { try {
	 * if(predajeRepository.findById(predajeId).isPresent() &&
	 * odeljenjeRepository.findById(odeljenjeId).isPresent()) { return new
	 * ResponseEntity<List<DrziNastavuEntity>>
	 * (drziNastavudao.findDrziNastavuByPredajeOdeljnje(predajeId, odeljenjeId),
	 * HttpStatus.OK); } else { return new ResponseEntity<RESTError>(new
	 * RESTError(1, "Odeljenje i razred nisu pronadjeni"), HttpStatus.NOT_FOUND); }
	 * } catch (Exception e) { return new ResponseEntity<RESTError>(new RESTError(2,
	 * "Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	 * } }
	 */
}
