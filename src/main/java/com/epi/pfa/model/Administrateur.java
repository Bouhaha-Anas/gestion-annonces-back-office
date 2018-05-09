package com.epi.pfa.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity( name="administrateurs" )
public class Administrateur  implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	private String nom;
	private String prenom;
	private String telephone;
	private String adresseMail;
	private String image;
	
	@OneToOne( cascade = CascadeType.ALL )
	@JoinColumn
	private Compte compte;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAdresseMail() {
		return adresseMail;
	}

	public void setAdresseMail(String adresseMail) {
		this.adresseMail = adresseMail;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
