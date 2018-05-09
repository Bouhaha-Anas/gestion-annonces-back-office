package com.epi.pfa.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epi.pfa.model.Produit;
import com.epi.pfa.service.ProduitService;


@RestController
public class ProduitController 
{
	@Autowired
	ProduitService produitService;
	
	@RequestMapping( value = "/offres", method = RequestMethod.GET )
	public ModelAndView pageListeOffre()
	{
		ModelAndView modelAndView = new ModelAndView();
		List<Produit> produitsActifs = produitService.getAllActiveProducts();
		List<Produit> produitsPasses = produitService.getAllPassedProducts();
		modelAndView.addObject("produitsActifs", produitsActifs);
		modelAndView.addObject("produitsPasses", produitsPasses);
		modelAndView.setViewName("listeProduits");
		return modelAndView;		
	}
	
	@RequestMapping( value = "/desactiverOffre/{id}", method = RequestMethod.PUT )
	public void pageListeOffreAfterDisabling(@PathVariable("id") Long id, HttpServletResponse response) throws IOException, ServletException
	{	
		Produit produit = produitService.getProduit(id);
		produit.setEstActive(false);
		produitService.updateProduit(produit);
		response.sendRedirect("/offres");
	}
	
}
