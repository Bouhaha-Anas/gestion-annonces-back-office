package com.epi.pfa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epi.pfa.model.Administrateur;
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
}
