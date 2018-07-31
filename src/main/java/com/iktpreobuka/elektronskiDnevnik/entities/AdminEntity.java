package com.iktpreobuka.elektronskiDnevnik.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.elektronskiDnevnik.enumerations.EKorisnikRole;

@Entity
@Table(name = "admin")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AdminEntity extends KorisnikEntity {

	@Column(nullable=false)
	private String email;
	
	@Version
	private Integer version;

	public AdminEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdminEntity(Integer id, String username, String password, String ime, String prezime,
			EKorisnikRole korisnikRole) {
		super(id, username, password, ime, prezime, korisnikRole);
		// TODO Auto-generated constructor stub
	}

	public AdminEntity(String email, Integer version) {
		super();
		this.email = email;
		this.version = version;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	

}
