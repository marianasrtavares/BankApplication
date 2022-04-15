package pt.mrdb.repository;

import pt.mrdb.database.DataBase;
import pt.mrdb.database.Operation;
import pt.mrdb.model.Account;
import pt.mrdb.model.Client;

public class AccountClientRepositoryImpl implements AccountClientRepository {

	@Override
	public void addSecondaryClient(Account account, Client client) {
		String query = "INSERT INTO accounts_clients " + "(account_id, client_id)" + "VALUES" + "(" + account.getId()
				+ "," + client.getId() + ");";
		DataBase.executeQuery(query, Operation.INSERT);

	}

	@Override
	public void removeAllSecondaryClients(Account account) {
		String query = "DELETE FROM accounts_clients WHERE account_id=" + account.getId() + ";";
		DataBase.executeQuery(query, Operation.DELETE);

	}

}
