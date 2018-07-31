package com.iktpreobuka.elektronskiDnevnik.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DrziNastavuDTO {
	
	private Integer idPredaje;
	
	private Integer idOdeljenje;

	public DrziNastavuDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getIdPredaje() {
		return idPredaje;
	}

	public void setIdPredaje(Integer idPredaje) {
		this.idPredaje = idPredaje;
	}

	public Integer getIdOdeljenje() {
		return idOdeljenje;
	}

	public void setIdOdeljenje(Integer idOdeljenje) {
		this.idOdeljenje = idOdeljenje;
	}

	}