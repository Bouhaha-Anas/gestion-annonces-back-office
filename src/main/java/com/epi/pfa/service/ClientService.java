package com.epi.pfa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epi.pfa.model.Client;
import com.epi.pfa.model.Compte;
import com.epi.pfa.repository.ClientRepository;

@Service
public class ClientService 
{	
	@Autowired
	ClientRepository clientRepository;
	
	public List<Client> getAllClients()
	{
		return (List<Client>) clientRepository.findAll();
	}
	
	public Client getClient(Long id)
	{
		return clientRepository.findOne(id);
	}
	
	@Transactional
	public void updateClient(Client client) 
	{
		clientRepository.save(client);
	}
	
	public Client findOneByCompte(Compte compte)
	{
		return clientRepository.findOneByCompte(compte);
	}
	
	public Client findOneByAdresseMail(String adresseMail)
	{
		return clientRepository.findOneByAdresseMail(adresseMail);
	}
	
	public List<Client> searchClientByNom(String nom)
	{
		return clientRepository.findAllByNomContainingIgnoreCase(nom);
	}
}
