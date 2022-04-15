package pt.mrdb.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import pt.mrdb.model.Account;
import pt.mrdb.model.Client;

public interface ClientRepository {
	

	Optional<Client> save(Client client); 
	
	List<Client> findAll();
	
	Optional<Client> findById(Integer id);
	
	Optional<Client> findByNib(String nif);
	
	void deleteById(Integer id);

}
