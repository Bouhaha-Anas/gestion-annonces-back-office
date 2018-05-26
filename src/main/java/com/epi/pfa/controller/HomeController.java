package com.epi.pfa.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epi.pfa.model.Administrateur;
import com.epi.pfa.model.Compte;
import com.epi.pfa.service.AdministrateurService;
import com.epi.pfa.service.CompteService;

@RestController
public class HomeController 
{
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private CompteService compteService;
	
	@Autowired
	private AdministrateurService administrateurService;
	
	@RequestMapping( value = "/home", method = RequestMethod.GET )
	public ModelAndView homePage()
	{
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login = auth.getName();
		Compte compte = compteService.findOneByLogin(login);
		Administrateur administrateur = administrateurService.findOneByCompte(compte);
		servletContext.setAttribute("administrateur", administrateur);
		modelAndView.setViewName("home");
		return modelAndView;
	}
}
