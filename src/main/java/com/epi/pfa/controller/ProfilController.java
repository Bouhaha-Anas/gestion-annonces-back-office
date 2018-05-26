package com.epi.pfa.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

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
public class ProfilController 
{
	@Autowired
	private CompteService compteService;
	
	@Autowired
	private AdministrateurService administrateurService;
	
	@RequestMapping( value="/profil", method = RequestMethod.GET )
	public ModelAndView pageProfil()
	{
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login = auth.getName();
		Compte compte = compteService.findOneByLogin(login);
		Administrateur administrateur = administrateurService.findOneByCompte(compte);
		
		modelAndView.addObject("administrateur", administrateur);
		modelAndView.setViewName("profil");
		return modelAndView;
	}
	
	@RequestMapping( value="/modificationProfil", method = RequestMethod.GET )
	public ModelAndView pageModificationProfil()
	{
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login = auth.getName();
		Compte compte = compteService.findOneByLogin(login);
		Administrateur administrateur = administrateurService.findOneByCompte(compte);
		
		modelAndView.addObject("administrateur", administrateur);
		modelAndView.setViewName("profil");
		return modelAndView;
	}
	
	@RequestMapping( value="/modificationProfil", method = RequestMethod.PUT )
	public ModelAndView modificationProfil(Administrateur administrateur, HttpServletRequest request) throws IOException
	{
		ModelAndView modelAndView = new ModelAndView();
		String mdp = request.getParameter("mdp");
		Compte compte = compteService.findOneButNotMe(administrateur.getCompte().getLogin(), administrateur.getCompte().getId());
		
		if( compte == null )
		{
			if( mdp.equals(administrateur.getCompte().getMotDePasse()) )
			{
				administrateurService.updateAdministrateur(administrateur);
				modelAndView.addObject("successMessage", "Votre profil est modifier avec succès");
			}
			else
			{
				modelAndView.addObject("errorMessage", "Vérifier le mot de passe saisi.");
				modelAndView.addObject("administrateur", administrateur);
			}
		}
		else
		{
			modelAndView.addObject("errorMessage", "Le nom d'utilisateur existe déjà, réessayer.");
			modelAndView.addObject("administrateur", administrateur);
		}
		
		modelAndView.setViewName("profil");
		return modelAndView;
	}
}
