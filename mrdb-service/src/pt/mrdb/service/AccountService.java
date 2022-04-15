package pt.mrdb.service;

import java.util.List;
import java.util.Set;

import pt.mrbd.exception.AccountException;
import pt.mrdb.model.Account;
import pt.mrdb.model.Client;

public interface AccountService {
	
	Account save(Account account) throws AccountException; 
	
	List<Account> getAll();
	
	Account getById(Integer id) throws AccountException;
	
	Account getByNib(Integer nib) throws AccountException;
	
	Account getByClient(Client client) throws AccountException;
	
	void deleteById(Integer id) throws AccountException;

	Set<Client> findAllSecondaryClients(Account account);

	List<Double> findAccountTransations(Account account);


}
