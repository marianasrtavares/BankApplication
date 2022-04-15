package pt.mrdb.repository;

import pt.mrdb.model.Account;
import pt.mrdb.model.Client;

public interface AccountClientRepository {
	
	void addSecondaryClient(Account account, Client client);
	
	void removeAllSecondaryClients(Account account);

}
