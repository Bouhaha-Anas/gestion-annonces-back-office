package com.epi.pfa.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epi.pfa.model.Categorie;
import com.epi.pfa.service.CategorieService;

@RestController
public class CategorieController 
{
	@Autowired
	private CategorieService categorieService;
	
	@RequestMapping( value ="/categories", method = RequestMethod.GET )
	public ModelAndView categorieListe()
	{
		ModelAndView modelAndView = new ModelAndView();
		List<Categorie> categories = categorieService.findAllCategories();
		modelAndView.addObject("categories", categories);
		modelAndView.setViewName("listeCategories");
		return modelAndView;
	}
	
	@RequestMapping( value ="/nouvelleCategorie", method = RequestMethod.GET )
	public ModelAndView pageAjoutCategorie()
	{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("categorie", new Categorie());
		modelAndView.setViewName("ajoutCategorie");
		return modelAndView;
	}
	
	@RequestMapping( value ="/nouvelleCategorie", method = RequestMethod.POST )
	public ModelAndView ajoutCategorie(Categorie categorie)
	{
		ModelAndView modelAndView = new ModelAndView();
		Categorie tempCategorie = categorieService.findOneByNom(categorie.getNom());
		if( tempCategorie == null )
		{
			categorieService.addCategorie(categorie);
			modelAndView.addObject("successMessage", "La catégorie est ajoutée avec succés" );
		}
		else
		{
			modelAndView.addObject("errorMessage", "Cette catégorie déjà existe, réessayez" );
			modelAndView.addObject("categorie", new Categorie());
		}

		modelAndView.setViewName("ajoutCategorie");
		return modelAndView;
	}
	
	@RequestMapping( value ="/modificationCategorie/{id}", method = RequestMethod.GET )
	public ModelAndView pageModifierCategorie(@PathVariable("id") Long id)
	{
		ModelAndView modelAndView = new ModelAndView();
		
		Categorie categorie = categorieService.findById(id);
		
		modelAndView.addObject("categorie", categorie);
		modelAndView.setViewName("modificationCategorie");
		return modelAndView;
	}
	
	@RequestMapping( value ="/modificationCategorie/{id}", method = RequestMethod.PUT )
	public ModelAndView modifierCategorie(@PathVariable("id") Long id, Categorie categorie)
	{
		ModelAndView modelAndView = new ModelAndView();
		
		Categorie tempCategorie = categorieService.findOneByNom(categorie.getNom());
		if( tempCategorie == null )
		{
			categorieService.updateCategorie(categorie);
			modelAndView.addObject("successMessage", "La catégorie est modifiée avec succés" );
		}
		else
		{
			categorie = categorieService.findById(id);
			modelAndView.addObject("errorMessage", "Cette catégorie déjà existe, réessayez" );
			modelAndView.addObject("categorie", categorie);
		}
		
		modelAndView.setViewName("modificationCategorie");
		return modelAndView;
	}
	
	@RequestMapping( value = "/resultatRechercheCategories", method = RequestMethod.POST )
	public ModelAndView searchProductByNom(HttpServletRequest request) throws IOException, ServletException
	{	
		ModelAndView modelAndView = new ModelAndView();
		String nomC = request.getParameter("nomC");
		List<Categorie> categories = categorieService.searchCategorie(nomC);
		modelAndView.addObject("categories", categories);
		modelAndView.setViewName("resultatRechercheCategories");
		return modelAndView;
	}
}
