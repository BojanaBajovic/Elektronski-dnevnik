package com.iktpreobuka.elektronskiDnevnik.entities;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.elektronskiDnevnik.enumerations.EKorisnikRole;
import com.iktpreobuka.elektronskiDnevnik.security.Views;

@JsonPropertyOrder({ "id", "ime", "prezime", "username", "password", "korisnikRole" }) 
@MappedSuperclass

public abstract class KorisnikEntity {
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@JsonView(Views.Public.class)
	private Integer id;
	
	@JsonView(Views.Admin.class)
	@Column(nullable=false)
	private String username;
	
	@JsonView(Views.Admin.class)
	@Column(nullable=false)
	private String password;
	
	@JsonView(Views.Public.class)
	@Column(nullable=false)
	private String ime;
	
	@JsonView(Views.Public.class)
	@Column(nullable=false)
	private String prezime;
	
	@Enumerated(EnumType.STRING)
	@JsonView(Views.Private.class)
	@Column
	private EKorisnikRole korisnikRole;
	
	public KorisnikEntity() {
		
	}

	public KorisnikEntity(Integer id, String username, String password, String ime, String prezime, EKorisnikRole korisnikRole) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.ime = ime;
		this.prezime = prezime;
		this.korisnikRole = korisnikRole;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public EKorisnikRole getKorisnikRole() {
		return korisnikRole;
	}

	public void setKorisnikRole(EKorisnikRole korisnikRole) {
		this.korisnikRole = korisnikRole;
	}
	
}
