package com.iktpreobuka.elektronskiDnevnik.dto;

public class PredajeDTO {
	
	private Integer idNastavnik;
	
	private Integer idPredmet;

	public PredajeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PredajeDTO(Integer idNastavnik, Integer idPredmet) {
		super();
		this.idNastavnik = idNastavnik;
		this.idPredmet = idPredmet;
	}

	public Integer getIdNastavnik() {
		return idNastavnik;
	}

	public void setIdNastavnik(Integer idNastavnik) {
		this.idNastavnik = idNastavnik;
	}

	public Integer getIdPredmet() {
		return idPredmet;
	}

	public void setIdPredmet(Integer idPredmet) {
		this.idPredmet = idPredmet;
	}

}
