package com.epi.pfa.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epi.pfa.model.Administrateur;
import com.epi.pfa.model.Compte;
import com.epi.pfa.service.AdministrateurService;
import com.epi.pfa.service.CompteService;
import com.epi.pfa.utilities.UploadingTask;

@RestController
public class AdministrateurController 
{
	public static final String CHEMIN_FICHIERS_CLIENTS = "D:/Ingenieurie/Semestre2/PFA/Work-Space/gestion-annonces-back-office/src/main/resources/static/uploaded-images/images-administrateurs/";

	@Autowired
	private AdministrateurService administrateurService;
	
	@Autowired
	private CompteService compteService;
	
	@RequestMapping( value = "/nouvelAdministrateur", method = RequestMethod.GET )
	public ModelAndView pageAjoutAdministrateur()
	{
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.addObject("administrateur", new Administrateur());
		modelAndView.setViewName("ajoutAdministrateur");
		
		return modelAndView;		
	}
	
	@RequestMapping( value = "/nouvelAdministrateur", method = RequestMethod.POST )
	public ModelAndView ajoutAdministrateur(Administrateur administrateur, HttpServletRequest req) throws IOException, ServletException
	{
		ModelAndView modelAndView = new ModelAndView();
		String errorMessage = null;
		Compte compte = compteService.findOneByLogin(administrateur.getCompte().getLogin());
		String mdp = req.getParameter("mdp");
		
		if(compte == null)
		{
			if( mdp.equals(administrateur.getCompte().getMotDePasse()) )
			{
				Part part = req.getPart("imageA");
				String nomFichier = UploadingTask.getNomFichier(part);
				if (nomFichier != null && !nomFichier.isEmpty()) 
				{	   
		            nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1).substring(nomFichier.lastIndexOf('\\') + 1);
		            UploadingTask.ecrireFichier(part, nomFichier, CHEMIN_FICHIERS_CLIENTS);
		            administrateur.setImage(nomFichier);
		            administrateur.getCompte().setEnabled(true);
		            String role = req.getParameter("role");
		            administrateur.getCompte().setRole(role);
		            administrateurService.addAdministrateur(administrateur);
		            modelAndView.addObject("successMessage", "L'administrateur est créé avec succés");
		        }
				else
				{
					errorMessage = "Veuillez choisir une photo de profil pour cet administrateur";
					modelAndView.addObject("errorMessage", errorMessage);
					modelAndView.addObject("administrateur", new Administrateur());
				}
			}
			else
			{
				errorMessage = "Vérifier le mot de passe saisi.";
				modelAndView.addObject("errorMessage", errorMessage);
				modelAndView.addObject("administrateur", new Administrateur());
			}
		}
		else
		{
			errorMessage = "Le nom d'utilisateur existe déjà, réessayer.";
			modelAndView.addObject("errorMessage", errorMessage);
		}
		
		modelAndView.setViewName("ajoutAdministrateur");
		return modelAndView;
	}
	
	@RequestMapping( value = "/administrateurs", method = RequestMethod.GET )
	public ModelAndView pageListeAdministrateur()
	{
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login = auth.getName();
		Compte compte = compteService.findOneByLogin(login);
		Administrateur administrateur = administrateurService.findOneByCompte(compte);
		List<Administrateur> administrateurs = administrateurService.getAllAdministrators();
		List<Administrateur> sAdministrateurs = administrateurService.getAllSuperAdministratorsButMe(administrateur.getId());
		System.out.println("TAAAAAAAAAAAAAAIILLEEEE"+sAdministrateurs.size());
		modelAndView.addObject("sAdministrateurs", sAdministrateurs);
		modelAndView.addObject("administrateurs", administrateurs);
		modelAndView.setViewName("listeAdministrateurs");
		
		return modelAndView;		
	}
	
	@RequestMapping( value = "/desactiverAdministrateur/{id}", method = RequestMethod.PUT )
	public void pageListeAdministrateurAfterDisabling(@PathVariable("id") Long id, HttpServletResponse response) throws IOException, ServletException
	{
		Administrateur administrateur = administrateurService.findOne(id);
		administrateur.getCompte().setEnabled(false);
		administrateurService.updateAdministrateur(administrateur);
		response.sendRedirect("/administrateurs");
	
	}
	
	@RequestMapping( value = "/activerAdministrateur/{id}", method = RequestMethod.PUT )
	public void pageListeAdministrateurAfterEnabling(@PathVariable("id") Long id, HttpServletResponse response) throws IOException, ServletException
	{
		Administrateur administrateur = administrateurService.findOne(id);
		administrateur.getCompte().setEnabled(true);
		administrateurService.updateAdministrateur(administrateur);
		response.sendRedirect("/administrateurs");
				
	}
	
	@RequestMapping( value = "/modificationAdministrateur/{id}", method = RequestMethod.GET )
	public ModelAndView pageModificationAdministrateur(@PathVariable("id") Long id)
	{
		ModelAndView modelAndView = new ModelAndView();
		Administrateur administrateur = administrateurService.findOne(id);
		modelAndView.addObject("administrateur", administrateur);
		modelAndView.setViewName("modificationAdministrateur");
		
		return modelAndView;		
	}
	
	@RequestMapping( value = "/modificationAdministrateur/{id}", method = RequestMethod.PUT )
	public ModelAndView modificationAdministrateur(@PathVariable("id") Long id, Administrateur administrateur, HttpServletRequest request) throws IOException, ServletException
	{
		ModelAndView modelAndView = new ModelAndView();
		String errorMessage = null;
		Compte compte = compteService.findOneButNotMe(administrateur.getCompte().getLogin(), administrateur.getId());
		
		if( compte == null )
		{
			String role = request.getParameter("role");
	        administrateur.getCompte().setRole(role);
	        modelAndView.addObject("successMessage", "Les informations de l'administrateur sont modifiées avec succés");
	        administrateurService.updateAdministrateur(administrateur);
		}
		else
		{
			errorMessage = "Le nom d'utilisateur existe déjà, réessayer.";
			modelAndView.addObject("successMessage", errorMessage);
			modelAndView.addObject("administrateur", administrateur);
		}
		
		modelAndView.setViewName("modificationAdministrateur");
		
		return modelAndView;		
	}
	
	@RequestMapping( value = "/resultatRechercheAdministrateurs", method = RequestMethod.POST )
	public ModelAndView searchProductByNom(HttpServletRequest request) throws IOException, ServletException
	{	
		ModelAndView modelAndView = new ModelAndView();
		String nomA = request.getParameter("nomA");
		List<Administrateur> administrateurs = administrateurService.searchAdministratorByNom(nomA);
		modelAndView.addObject("administrateurs", administrateurs);
		modelAndView.setViewName("resultatRechercheAdministrateurs");
		return modelAndView;
	}
}
