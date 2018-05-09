package com.epi.pfa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epi.pfa.model.Administrateur;
import com.epi.pfa.model.Compte;
import com.epi.pfa.repository.AdministrateurRepository;

@Service
public class AdministrateurService 
{
	@Autowired
	private AdministrateurRepository administrateurRepository;
	
	public void addAdministrateur(Administrateur administrateur)
	{
		administrateurRepository.save(administrateur);
	}
	
	public Administrateur findOne(Long id)
	{
		return administrateurRepository.findOne(id);
	}
	
	public Administrateur findOneByAdresseMail(String adresseMail)
	{
		return administrateurRepository.findOneByAdresseMail(adresseMail);
	}
	
	public Administrateur findOneByCompte(Compte compte) 
	{
		return administrateurRepository.findOneByCompte(compte);
	}
	
	public List<Administrateur> getAllButMe(Long idA)
	{
		return administrateurRepository.getAllButMe(idA);
	}

	public void updateAdministrateur(Administrateur administrateur) 
	{
		administrateurRepository.save(administrateur);
	}
}
