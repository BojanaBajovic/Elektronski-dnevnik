package com.iktpreobuka.elektronskiDnevnik.dto;

import javax.validation.constraints.Max;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OdeljenjeDTO {
	
	@Min(value=1, message="Odeljenje mora biti minimum 1!")
	@Max(value=5, message="Odeljenje mora biti maksimum 10!")
	@NotNull(message = "Odeljenje must be provided.")
	@JsonProperty("brojOdeljenja")
	private String brojOdeljenja;
	
	private Integer idRazred;

	public OdeljenjeDTO() {
		
	}
	
	public OdeljenjeDTO(String brojOdeljenja, Integer idRazred) {
		super();
		this.brojOdeljenja = brojOdeljenja;
		this.idRazred = idRazred;
	}

	public String getBrojOdeljenja() {
		return brojOdeljenja;
	}

	public void setBrojOdeljenja(String brojOdeljenja) {
		this.brojOdeljenja = brojOdeljenja;
	}

	public Integer getIdRazred() {
		return idRazred;
	}

	public void setIdRazred(Integer idRazred) {
		this.idRazred = idRazred;
	}

}
