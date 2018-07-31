package com.iktpreobuka.elektronskiDnevnik.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RazredDTO {

	@NotNull(message="Broj razreda must be provided.")
	@Min(value=1, message="Razred mora biti minimum 1!")
	@Max(value=8, message="Razred mora biti maksimum 8!")
	@JsonProperty("brojRazreda")
	private Integer brojRazreda;

	public RazredDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RazredDTO(Integer brojRazreda) {
		super();
		this.brojRazreda = brojRazreda;
	}

	public Integer getBrojRazreda() {
		return brojRazreda;
	}

	public void setBrojRazreda(Integer brojRazreda) {
		this.brojRazreda = brojRazreda;
	}

	
}
