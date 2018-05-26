package com.epi.pfa.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epi.pfa.model.Produit;
import com.epi.pfa.repository.ProduitRepository;

@Service
public class ProduitService 
{
	@Autowired
	private ProduitRepository produitRepository;
	
	public void addProduit(Produit produit)
	{
		produitRepository.save(produit);
	}
	
	public List<Produit> getAllActiveProducts()
	{
		return produitRepository.findAllByEstActiveTrue();
	}
	
	public List<Produit> getAllPassedProducts()
	{
		return produitRepository.findAllByEstActiveFalse();
	}
	
	public List<Produit> findByNomLike(String nom)
	{
		return produitRepository.findByNomLike(nom);
	}
	
	public Produit getProduit(Long id)
	{
		return produitRepository.findOne(id);
	}
	
	public List<Produit> findByDateFin(Date date)
	{
		return produitRepository.findByDateFin(date);
	}
	
	public void updateProduit(Produit produit)
	{
		produitRepository.save(produit);
	}
	
	public List<Produit> getByDateAndCategorieId(Date date, Long id)
	{
		return produitRepository.findAllByDateDebutBeforeAndCategorieIdIn(date, id);
	}

	public List<Produit> findAllByNom(String nomP) 
	{
		return produitRepository.findAllByNomContainingIgnoreCaseOrderByEstActive(nomP);
	}
}
