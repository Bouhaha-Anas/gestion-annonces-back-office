package com.epi.pfa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.epi.pfa.model.Administrateur;
import com.epi.pfa.model.Compte;


public interface AdministrateurRepository extends JpaRepository<Administrateur, Long> 
{
	Administrateur findOneByCompte(Compte compte);
	Administrateur findOneByAdresseMail(String adresseMail);
	
	@Query( value="select * from administrateurs where id != :idA", nativeQuery = true )
	List<Administrateur> getAllButMe(@Param("idA") Long idA);
}
