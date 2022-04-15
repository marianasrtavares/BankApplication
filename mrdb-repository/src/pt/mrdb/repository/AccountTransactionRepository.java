package pt.mrdb.repository;

import pt.mrdb.model.Account;

public interface AccountTransactionRepository {

	void addAccountTrasation(Account account);

	void removeAccountTrasation(Account account);

}
