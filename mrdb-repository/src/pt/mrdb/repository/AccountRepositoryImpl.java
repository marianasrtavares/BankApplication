package pt.mrdb.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import pt.mrdb.database.AccountTransationRepositoryImpl;
import pt.mrdb.database.DataBase;
import pt.mrdb.database.Operation;
import pt.mrdb.model.Account;
import pt.mrdb.model.Client;

public class AccountRepositoryImpl implements AccountRepository {
	private AccountClientRepository accountClientRepository = new AccountClientRepositoryImpl();
	private AccountTransactionRepository accountTransationRepository = new AccountTransationRepositoryImpl();

	@Override
	public Optional<Account> save(Account account) {
		if (isClientOverEighteen(account)) {
			return Optional.empty();
		}
		if (account.getId() == null) {
			String query = "INSERT INTO accounts " + "(nib, client_primary, balance)" + "VALUES" + "("
					+ account.getNib() + "," + account.getPrimaryClient().getId() + "," + account.getBalance() + ");";
			DataBase.executeQuery(query, Operation.INSERT);

			ResultSet rs = DataBase.executeQuery("SELECT * FROM accounts WHERE id= (SELECT MAX(id) FROM accounts)",
					Operation.SELECT);
			Optional<Account> savedAccount = extractObject(rs);

			// transaction list
			accountTransationRepository.addAccountTrasation(account);
			
			account.setTransationHistory(findAccountTransations(account));

			// secondary clients

			for (Client client : account.getSecondaryClient()) {
				accountClientRepository.addSecondaryClient(savedAccount.get(), client);
			}
			

			return savedAccount;

		} else {

			// secondary clients

			accountClientRepository.removeAllSecondaryClients(account);

			for (Client client : account.getSecondaryClient()) {
				accountClientRepository.addSecondaryClient(account, client);
			}

			// transaction list
			accountTransationRepository.addAccountTrasation(account);
			
			account.setTransationHistory(findAccountTransations(account));
			
			//account

			String query = "UPDATE accounts SET client_primary=" + account.getPrimaryClient().getId() + ", balance="
					+ account.getBalance() + "WHERE id =" + account.getId() + ";";
			DataBase.executeQuery(query, Operation.UPDATE);

			ResultSet rs = DataBase.executeQuery("SELECT * FROM accounts WHERE id=" + account.getId(),
					Operation.SELECT);
			return extractObject(rs);
		}
	}

	@Override
	public List<Account> findAll() {
		String query = "SELECT * FROM accounts;";
		ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
		return extractList(rs);
	}

	@Override
	public Optional<Account> findById(Integer id) {

		String query = "SELECT * FROM accounts WHERE id=" + id + ";";
		ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
		return extractObject(rs);
	}

	@Override
	public Optional<Account> findByNib(Integer nib) {
		String query = "SELECT * FROM accounts WHERE nib=" + nib + ";";
		ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
		return extractObject(rs);
	}

	@Override
	public Optional<Account> findByClient(Client client) {
		String query = "SELECT * FROM accounts WHERE client_primary=" + client.getId() + ";";
		ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
		return extractObject(rs);
	}

	@Override
	public Set<Client> findAllSecondaryClients(Account account) {
		String query = "SELECT * FROM accounts_clients WHERE account_id=" + account.getId() + ";";
		ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
		return extractListSecondaryClient(rs);
	}
	
	@Override
	public List<Double> findAccountTransations(Account account) {
		String query = "SELECT * FROM accounts_transations WHERE account_id=" + account.getId() + ";";
		ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
		return extractListAccountTransation(rs);
	}

	private List<Double> extractListAccountTransation(ResultSet rs) {
		List<Double> transations = new ArrayList<>();
		try {
			while (rs.next()) {
				Double transation = rs.getDouble(2);
						transations.add(transation);

			}
		} catch (SQLException e) {
			e.getStackTrace();
		}
		return transations;		
	}

	private Set<Client> extractListSecondaryClient(ResultSet rs) {
		Set<Client> secondaryClients = new HashSet<Client>();
		try {
			while (rs.next()) {
				Client client = new Client();
				client.setId(rs.getInt(2));
				secondaryClients.add(client);

			}
		} catch (SQLException e) {
			e.getStackTrace();
		}
		return secondaryClients;
	}

	@Override
	public int countAccountByClient(Client client) {
		try {
			String query = "SELECT COUNT(id) FROM accounts WHERE client_primary=" + client.getId() + ";";
			ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
			return rs.getInt(1);
		} catch (SQLException e) {
			System.out.println(e.getStackTrace());
		}
		throw new RuntimeException("Client does not have an account");

	}

	@Override
	public void deleteById(Integer id) {
		String query = "DELETE FROM accounts WHERE id=" + id + ";";
		DataBase.executeQuery(query, Operation.DELETE);

	}

	private List<Account> extractList(ResultSet rs) {
		List<Account> accounts = new ArrayList<>();
		try {
			while (rs.next()) {
				Account account = buildObject(rs);
				accounts.add(account);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return accounts;
	}

	private Optional<Account> extractObject(ResultSet rs) {
		try {
			if (rs.next()) {
				Account account = buildObject(rs);
				return Optional.of(account);
			}
		} catch (SQLException e) {
			e.getStackTrace();
		}
		return Optional.empty();

	}

	private Account buildObject(ResultSet rs) {

		Account account = new Account();
		try {
			Client client = new Client();
			account.setId(rs.getInt(1));
			account.setNib(rs.getInt(2));
			account.setBalance(rs.getDouble(4));
			client.setId(rs.getInt(3));
			account.setPrimaryClient(client);


		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account;

	}

	@Override
	public boolean isClientOverEighteen(Account account) {
		LocalDate date = null;
		ResultSet rs = null;
		try {
			String query = "SELECT date_of_birth FROM clients INNER JOIN accounts ON clients.id=accounts.client_primary WHERE accounts.client_primary= "
					+ account.getPrimaryClient().getId() + " AND accounts.id=" + account.getId() + ";";
			rs = DataBase.executeQuery(query, Operation.SELECT);
			if (rs.next()) {
				date = rs.getDate(1).toLocalDate();

				int presentYear = LocalDate.now().getYear();

				if (presentYear - date.getYear() < 18) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
