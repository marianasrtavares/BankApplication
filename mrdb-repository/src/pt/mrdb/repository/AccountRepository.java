package pt.mrdb.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import pt.mrdb.model.Account;
import pt.mrdb.model.Client;

public interface AccountRepository {

	Optional<Account> save(Account account); 

	List<Account> findAll();

	Optional<Account> findById(Integer id);

	Optional<Account> findByNib(Integer nib);

	Optional<Account> findByClient(Client client);
	
	int countAccountByClient(Client client);

	void deleteById(Integer id);

	boolean isClientOverEighteen(Account account);

	Set<Client> findAllSecondaryClients(Account account);

	List<Double> findAccountTransations(Account account);

}
