package com.iktpreobuka.elektronskiDnevnik.entities;

import java.util.ArrayList;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "ucenik")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class UcenikEntity extends KorisnikEntity {
	
	@JsonManagedReference
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "roditelj")
	private RoditeljEntity roditelj;
	
	@JsonManagedReference
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "odeljenje")
	private OdeljenjeEntity odeljenje;
	
	@OneToMany(mappedBy = "ucenik", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<OcenaEntity> ocena = new ArrayList<>();
	
	@Version
	private Integer version;

	public UcenikEntity() {
		super();
		
	}

	public RoditeljEntity getRoditelj() {
		return roditelj;
	}

	public void setRoditelj(RoditeljEntity roditelj) {
		this.roditelj = roditelj;
	}

	public OdeljenjeEntity getOdeljenje() {
		return odeljenje;
	}

	public void setOdeljenje(OdeljenjeEntity odeljenje) {
		this.odeljenje = odeljenje;
	}

	public List<OcenaEntity> getOcena() {
		return ocena;
	}

	public void setOcena(List<OcenaEntity> ocena) {
		this.ocena = ocena;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
