package com.iktpreobuka.elektronskiDnevnik.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PredmetDTO {

	@NotNull(message="Naziv predmeta must be provided.")
	//@JsonProperty("naziv_predmeta")
	private String nazivPredmeta;
	
	@NotNull(message="Fond must be provided.")
	//@JsonProperty("fond")
	private Integer fond;

	public PredmetDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getNazivPredmeta() {
		return nazivPredmeta;
	}

	public void setNazivPredmeta(String nazivPredmeta) {
		this.nazivPredmeta = nazivPredmeta;
	}

	public Integer getFond() {
		return fond;
	}

	public void setFond(Integer fond) {
		this.fond = fond;
	}
}
