package pt.mrdb.service;

import java.util.List;
import java.util.Set;

import pt.mrbd.exception.ClientException;
import pt.mrdb.model.Account;
import pt.mrdb.model.Client;

public interface ClientService {
	
    Client save(Client client) throws ClientException; 
	
	List<Client> getAll();
	
	Client getById(Integer id) throws ClientException;
	
	Client getByNib(String nif) throws ClientException;
	
	void deleteById(Integer id) throws ClientException;
		

}
