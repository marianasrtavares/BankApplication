package pt.mrdb.database;

import pt.mrdb.model.Account;
import pt.mrdb.repository.AccountTransactionRepository;

public class AccountTransationRepositoryImpl implements AccountTransactionRepository {

	@Override
	public void addAccountTrasation(Account account) {
		String query = "INSERT INTO accounts_transations " + "(account_id, balance)" + "VALUES" + "(" + account.getId()
				+ ", "+account.getBalance()+");";
		DataBase.executeQuery(query, Operation.INSERT);

	}

	@Override
	public void removeAccountTrasation(Account account) {
		String query = "DELETE FROM accounts_clients WHERE account_id=" + account.getId() + ";";
		DataBase.executeQuery(query, Operation.DELETE);

	}
}