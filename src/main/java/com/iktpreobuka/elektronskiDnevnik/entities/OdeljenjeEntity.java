package com.iktpreobuka.elektronskiDnevnik.entities;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "odeljenje")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OdeljenjeEntity {
	
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Integer id;
	
	@Column(nullable=false)
	private String brojOdeljenja;
	
	@OneToMany(mappedBy = "odeljenje", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<UcenikEntity> ucenik = new ArrayList<>();
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "razred")
	@JsonManagedReference
	private RazredEntity razred;
	
	@OneToMany(mappedBy = "odeljenje", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<DrziNastavuEntity> drziNastavu = new ArrayList<>();
	
	@Version
	private Integer version;


	public OdeljenjeEntity() {
	}
	
	public OdeljenjeEntity(Integer id, String brojOdeljenja, List<UcenikEntity> ucenik, RazredEntity razred,
			List<DrziNastavuEntity> drziNastavu, Integer version) {
		super();
		this.id = id;
		this.brojOdeljenja = brojOdeljenja;
		this.ucenik = ucenik;
		this.razred = razred;
		this.drziNastavu = drziNastavu;
		this.version = version;
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBrojOdeljenja() {
		return brojOdeljenja;
	}


	public void setBrojOdeljenja(String brojOdeljenja) {
		this.brojOdeljenja = brojOdeljenja;
	}


	public List<UcenikEntity> getUcenik() {
		return ucenik;
	}


	public void setUcenik(List<UcenikEntity> ucenik) {
		this.ucenik = ucenik;
	}


	public RazredEntity getRazred() {
		return razred;
	}


	public void setRazred(RazredEntity razred) {
		this.razred = razred;
	}

	public Integer getVersion() {
		return version;
	}


	public void setVersion(Integer version) {
		this.version = version;
	}


	public List<DrziNastavuEntity> getDrziNastavu() {
		return drziNastavu;
	}


	public void setDrziNastavu(List<DrziNastavuEntity> drziNastavu) {
		this.drziNastavu = drziNastavu;
	}
}
