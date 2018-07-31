package com.iktpreobuka.elektronskiDnevnik.dto;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OcenaDTO {
	
	/*@JsonProperty("id")
	private Integer id;*/
	@Min(value=1, message="Ocena mora biti minimum 1!")
	@Max(value=5, message="Ocena mora biti maksimum 5!")
	@NotNull(message = "Ocena must be provided.") 
	@JsonProperty("vrednostOcene")
	private Integer vrednostOcene;
		
	//@NotNull
	@JsonProperty("datum")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone="Europe/Belgrade")
	private LocalDate datum;
	
	//@NotNull
	@JsonProperty("polugodiste")
	private Integer polugodiste;
	
	private Integer idDrziNastavu;
	
	private Integer idUcenik;
	
	public OcenaDTO() {
		super();
	}

	public OcenaDTO(Integer vrednostOcene, LocalDate datum, Integer polugodiste, Integer idDrziNastavu,Integer idUcenik ) {
		super();
		this.vrednostOcene = vrednostOcene;
		this.datum = datum;
		this.polugodiste = polugodiste;
		this.idDrziNastavu = idDrziNastavu;
		this.idUcenik = idUcenik;
	}

	public Integer getVrednostOcene() {
		return vrednostOcene;
	}

	public void setVrednostOcene(Integer vrednostOcene) {
		this.vrednostOcene = vrednostOcene;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	public Integer getPolugodiste() {
		return polugodiste;
	}

	public void setPolugodiste(Integer polugodiste) {
		this.polugodiste = polugodiste;
	}

	public Integer getIdDrziNastavu() {
		return idDrziNastavu;
	}

	public void setIdDrziNastavu(Integer idDrziNastavu) {
		this.idDrziNastavu = idDrziNastavu;
	}

	public Integer getIdUcenik() {
		return idUcenik;
	}

	public void setIdUcenik(Integer idUcenik) {
		this.idUcenik = idUcenik;
	}

}
