package com.epi.pfa.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epi.pfa.model.Administrateur;

@RestController
public class AdministrateurController 
{
	@RequestMapping( value = "/nouvelAdministrateur", method = RequestMethod.GET )
	public ModelAndView pageAjoutAdministrateur()
	{
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.addObject("administrateur", new Administrateur());
		modelAndView.setViewName("ajoutAdministrateur");
		
		return modelAndView;		
	}
	
	@RequestMapping( value = "/nouvelAdministrateur", method = RequestMethod.POST )
	public ModelAndView ajoutAdministrateur()
	{
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.addObject("administrateur", new Administrateur());
		modelAndView.setViewName("ajoutAdministrateur");
		
		return modelAndView;		
	}
}
