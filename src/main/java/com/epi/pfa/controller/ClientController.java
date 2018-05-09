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

import com.epi.pfa.model.Client;
import com.epi.pfa.service.ClientService;

@RestController
public class ClientController 
{
	@Autowired
	private ClientService clientService;
	
	
	@RequestMapping( value="/clients", method= RequestMethod.GET )
	public ModelAndView listeEntrepreneursPage()
	{
		ModelAndView modelAndView = new ModelAndView();
		List<Client> clients = clientService.getAllClients();
		modelAndView.addObject("clients", clients);
		modelAndView.setViewName("listeClients");
		return modelAndView;
	}
	
	@RequestMapping( value="/modificationClient/{id}", method= RequestMethod.GET )
	public ModelAndView pageModificationEntrepeneur(@PathVariable("id") Long id)
	{
		ModelAndView modelAndView = new ModelAndView();
		Client client = clientService.getClient(id);
		modelAndView.addObject("client", client);
		modelAndView.setViewName("modificationClient");
		return modelAndView;
	}
	
	@RequestMapping( value="/modificationClient/{id}", method= RequestMethod.PUT )
	public ModelAndView modificationEntrepeneur(@PathVariable("id") Long id, Client client) throws IOException, ServletException
	{
		ModelAndView modelAndView = new ModelAndView();
		String errorMessage = null;
		Client tempClient = clientService.findOneByAdresseMail(client.getAdresseMail());
		
		if(tempClient != null)
		{
			if( tempClient.getId() != client.getId() )
			{
				errorMessage = "L'adresse Mail est déjà utilisée, veuillez réessayer";
				modelAndView.addObject("client", client);
				modelAndView.addObject("errorMessage", errorMessage);
			}
			else
			{
		        modelAndView.addObject("successMessage", "Les informations du client sont modifiées avec succés");
		        clientService.updateClient(client);
			}
		}
		
		modelAndView.setViewName("modificationClient");
		
		return modelAndView;
	}
	
	@RequestMapping( value = "/desactiverClient/{id}", method = RequestMethod.PUT )
	public void pageListeAdministrateurAfterDisabling(@PathVariable("id") Long id, HttpServletResponse response) throws IOException, ServletException
	{
		Client client = clientService.getClient(id);
		client.getCompte().setEnabled(false);
		clientService.updateClient(client);
		response.sendRedirect("/clients");
	}
	
	@RequestMapping( value = "/activerClient/{id}", method = RequestMethod.PUT )
	public void pageListeAdministrateurAfterEnabling(@PathVariable("id") Long id, HttpServletResponse response) throws IOException, ServletException
	{
		Client client = clientService.getClient(id);
		client.getCompte().setEnabled(true);
		clientService.updateClient(client);
		response.sendRedirect("/clients");		
	}
}
