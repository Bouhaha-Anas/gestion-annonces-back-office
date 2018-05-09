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

import com.epi.pfa.model.Entrepreneur;
import com.epi.pfa.service.EntrepreneurService;

@RestController
public class EntrepreneurController 
{
	@Autowired
	private EntrepreneurService entrepreneurService;
	
	@RequestMapping( value="/entrepreneurs", method= RequestMethod.GET )
	public ModelAndView listeEntrepreneursPage()
	{
		ModelAndView modelAndView = new ModelAndView();
		List<Entrepreneur> entrepreneurs = entrepreneurService.getAllEntrepreneurs();
		modelAndView.addObject("entrepreneurs", entrepreneurs);
		modelAndView.setViewName("listeEntrepreneurs");
		return modelAndView;
	}
	
	@RequestMapping( value="/modificationEntrepreneur/{id}", method= RequestMethod.GET )
	public ModelAndView pageModificationEntrepeneur(@PathVariable("id") Long id)
	{
		ModelAndView modelAndView = new ModelAndView();
		Entrepreneur entrepreneur = entrepreneurService.getEntrepreneur(id);
		modelAndView.addObject("entrepreneur", entrepreneur);
		modelAndView.setViewName("modificationEntrepreneur");
		return modelAndView;
	}
	
	@RequestMapping( value="/modificationEntrepreneur/{id}", method= RequestMethod.PUT )
	public ModelAndView modificationEntrepeneur(@PathVariable("id") Long id, Entrepreneur entrepreneur) throws IOException, ServletException
	{
		ModelAndView modelAndView = new ModelAndView();
		String errorMessage = null;
		Entrepreneur tempEntrepreneur = entrepreneurService.findOneByAdresseMail(entrepreneur.getAdresseMail());
		
		if(tempEntrepreneur != null)
		{
			if( tempEntrepreneur.getId() != entrepreneur.getId() )
			{
				errorMessage = "L'adresse Mail est déjà utilisée, veuillez réessayer";
				modelAndView.addObject("entrepreneur", entrepreneur);
				modelAndView.addObject("errorMessage", errorMessage);
			}
			else
			{
		        modelAndView.addObject("successMessage", "Les informations de l'entrepreneur sont modifiées avec succés");
		        entrepreneurService.updateEntrepreneur(entrepreneur);
			}
		}
		
		modelAndView.setViewName("modificationEntrepreneur");
		
		return modelAndView;
	}
	
	@RequestMapping( value = "/desactiverEntrepreneur/{id}", method = RequestMethod.PUT )
	public void pageListeAdministrateurAfterDisabling(@PathVariable("id") Long id, HttpServletResponse response) throws IOException, ServletException
	{
		Entrepreneur entrepreneur = entrepreneurService.getEntrepreneur(id);
		entrepreneur.getCompte().setEnabled(false);
		entrepreneurService.updateEntrepreneur(entrepreneur);
		response.sendRedirect("/entrepreneurs");
	}
	
	@RequestMapping( value = "/activerEntrepreneur/{id}", method = RequestMethod.PUT )
	public void pageListeAdministrateurAfterEnabling(@PathVariable("id") Long id, HttpServletResponse response) throws IOException, ServletException
	{
		Entrepreneur entrepreneur = entrepreneurService.getEntrepreneur(id);
		entrepreneur.getCompte().setEnabled(true);
		entrepreneurService.updateEntrepreneur(entrepreneur);
		response.sendRedirect("/entrepreneurs");		
	}
}
