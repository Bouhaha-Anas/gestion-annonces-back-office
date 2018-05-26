package com.epi.pfa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epi.pfa.model.Administrateur;
import com.epi.pfa.model.Compte;


public interface AdministrateurRepository extends JpaRepository<Administrateur, Long> 
{
	Administrateur findOneByCompte(Compte compte);
	
	Administrateur findOneByAdresseMail(String adresseMail);
	 
	List<Administrateur> findAllByCompteRoleIn(String role);
	
	List<Administrateur> findAllByIdNotInAndCompteRoleIn(Long idA, String nom);
	
	List<Administrateur> findAllByNomContainingIgnoreCaseOrderByCompteRole(String nom);
}
