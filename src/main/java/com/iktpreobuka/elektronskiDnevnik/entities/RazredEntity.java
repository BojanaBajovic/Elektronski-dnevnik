package com.iktpreobuka.elektronskiDnevnik.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "razred")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RazredEntity {
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Integer id;
	
	@Column(nullable=false)
	private Integer brojRazreda;
	
	@OneToMany(mappedBy = "razred", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<OdeljenjeEntity> odeljenje;
	
	@OneToMany(mappedBy = "razred", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<RazredImaPredmetEntity> razredImaPredmet;
	
	@Version
	private Integer version;

	public RazredEntity() {
	
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBrojRazreda() {
		return brojRazreda;
	}

	public void setBrojRazreda(Integer brojRazreda) {
		this.brojRazreda = brojRazreda;
	}

	public List<OdeljenjeEntity> getOdeljenje() {
		return odeljenje;
	}

	public void setOdeljenje(List<OdeljenjeEntity> odeljenje) {
		this.odeljenje = odeljenje;
	}

	public List<RazredImaPredmetEntity> getRazredImaPredmet() {
		return razredImaPredmet;
	}

	public void setRazredImaPredmet(List<RazredImaPredmetEntity> razredImaPredmet) {
		this.razredImaPredmet = razredImaPredmet;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
