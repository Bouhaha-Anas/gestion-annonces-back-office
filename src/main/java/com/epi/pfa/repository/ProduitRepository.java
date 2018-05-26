package com.epi.pfa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epi.pfa.model.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long>
{
	List<Produit> findAllByEstActiveTrue();
	
	List<Produit> findAllByEstActiveFalse();
	
	List<Produit> findByNomLike(String nom);
	
	List<Produit> findByDateFin(Date date);
	
	List<Produit> findAllByDateDebutBeforeAndCategorieIdIn(Date date, Long id);
	
	List<Produit> findAllByNomContainingIgnoreCaseOrderByEstActive(String nomP);
}
