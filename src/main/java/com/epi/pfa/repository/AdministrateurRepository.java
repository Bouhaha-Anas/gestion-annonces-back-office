package com.epi.pfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epi.pfa.model.Administrateur;
import com.epi.pfa.model.Compte;

public interface AdministrateurRepository extends JpaRepository<Administrateur, Long> 
{
	Administrateur findOneByCompte(Compte compte);
}
