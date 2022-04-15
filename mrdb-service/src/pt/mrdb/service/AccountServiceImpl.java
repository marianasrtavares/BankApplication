package pt.mrdb.service;

import java.util.List;
import java.util.Set;

import pt.mrbd.exception.AccountException;
import pt.mrdb.model.Account;
import pt.mrdb.model.Client;
import pt.mrdb.repository.AccountRepository;
import pt.mrdb.repository.AccountRepositoryImpl;

public class AccountServiceImpl implements AccountService {
	
	AccountRepository accountRepository = new AccountRepositoryImpl();

	@Override
	public Account save(Account account) throws AccountException {
		
//			LocalDate today = LocalDate.now();
//			int year = today.getYear();
			
			if (account.getPrimaryClient() == null) {
				throw new AccountException("Account must have primary client");
			}
			if (accountRepository.isClientOverEighteen(account)) {
				throw new AccountException("Client age is not over 18");
			}

			if (account.getSecondaryClient() != null && account.getSecondaryClient().size() > 4) {
				throw new AccountException("Overpast number of secondary clients");
			}
			if (account.getBalance() < 50) {
				throw new AccountException("Below the minimum mandatory balance of 50€");
			}
			return accountRepository.save(account).get();
		
	}

	@Override
	public List<Account> getAll() {
		return accountRepository.findAll();
	}

	@Override
	public Account getById(Integer id) {

		return accountRepository.findById(id).get();

	}
	

	@Override
	public Account getByNib(Integer nib) throws AccountException {

		return accountRepository.findByNib(nib).get();

	}

	@Override
	public Account getByClient(Client client) throws AccountException {

		return accountRepository.findByClient(client).get();

	}

	@Override
	public void deleteById(Integer id) throws AccountException {

		accountRepository.deleteById(id);

	}
	@Override
	public Set<Client> findAllSecondaryClients(Account account) {
		// TODO Auto-generated method stub
		return accountRepository.findAllSecondaryClients(account);
	}
	
	@Override
	public List<Double> findAccountTransations(Account account) {
		return accountRepository.findAccountTransations(account);
	}
	}


