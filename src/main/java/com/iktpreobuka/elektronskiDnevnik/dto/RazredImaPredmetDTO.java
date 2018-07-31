package com.iktpreobuka.elektronskiDnevnik.dto;

public class RazredImaPredmetDTO {

	private Integer idPredmet;
	
	private Integer idRazred;

	public RazredImaPredmetDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public RazredImaPredmetDTO(Integer idPredmet, Integer idRazred) {
		super();
		this.idPredmet = idPredmet;
		this.idRazred = idRazred;
	}

	public Integer getIdPredmet() {
		return idPredmet;
	}

	public void setIdPredmet(Integer idPredmet) {
		this.idPredmet = idPredmet;
	}

	public Integer getIdRazred() {
		return idRazred;
	}

	public void setIdRazred(Integer idRazred) {
		this.idRazred = idRazred;
	}
}
