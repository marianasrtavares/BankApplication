package pt.mrdb.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pt.mrdb.database.DataBase;
import pt.mrdb.database.Operation;
import pt.mrdb.model.Account;
import pt.mrdb.model.Card;
import pt.mrdb.model.Client;
import pt.mrdb.model.CreditCard;
import pt.mrdb.model.DebitCard;

public class CardRepositoryImpl implements CardRepository {

	@Override
	public Optional<Card> save(Card card) {
		if (card.getId() == null && card instanceof CreditCard) {
			CreditCard creditCard = (CreditCard) card;
			String queryInsertCreditCard = "INSERT INTO cards (client_id, account_id, pin, plafond) VALUES ("
					+ card.getClient().getId() + ", " + card.getAccount().getId() + ", '" + card.getPin() + "',"
					+ creditCard.getPlafond() + ");";
			DataBase.executeQuery(queryInsertCreditCard, Operation.INSERT);
			ResultSet rsInsertCreditCard = DataBase
					.executeQuery("SELECT * FROM cards WHERE id= (SELECT MAX(id) FROM cards)", Operation.SELECT);
			return extractObject(rsInsertCreditCard);
		}
		if (card.getId() == null && card instanceof DebitCard) {
			DebitCard debitCard = (DebitCard) card;
			String queryInsertDebitCard = "INSERT INTO cards (client_id, account_id, pin, daily_withdrawal) VALUES ("
					+ card.getClient().getId() + ", " + card.getAccount().getId() + ", '" + card.getPin() + "',"
					+ debitCard.getDailyWithdrawal() + ");";
			DataBase.executeQuery(queryInsertDebitCard, Operation.INSERT);
			ResultSet rsInsertDebitCard = DataBase
					.executeQuery("SELECT * FROM cards WHERE id= (SELECT MAX(id) FROM cards)", Operation.SELECT);
			return extractObject(rsInsertDebitCard);
		} else {
			if (isCreditCard(card.getId()) || card instanceof CreditCard) {
				CreditCard creditCard = (CreditCard) card;
				String query = "UPDATE cards SET pin ='" + card.getPin() + "', isOriginalPin=" + 0 + ",plafond="
						+ creditCard.getPlafond() + " WHERE id=" + card.getId() + ";";
				DataBase.executeQuery(query, Operation.UPDATE);
				ResultSet rs = DataBase.executeQuery("SELECT * FROM cards WHERE id= " + card.getId() + ";",
						Operation.SELECT);
				return extractObject(rs);

			} else if (card instanceof DebitCard) {
				DebitCard debitCard = (DebitCard) card;
				String query = "UPDATE cards SET pin ='" + card.getPin() + "', isOriginalPin=" + 0
						+ ", daily_withdrawal=" + debitCard.getDailyWithdrawal() + " WHERE id=" + card.getId() + ";";
				DataBase.executeQuery(query, Operation.UPDATE);
				ResultSet rs = DataBase.executeQuery("SELECT * FROM cards WHERE id= " + card.getId() + ";",
						Operation.SELECT);
				return extractObject(rs);
			}
			return Optional.empty();

		}

	}

	@Override
	public List<Card> findAll() {
		String query = "SELECT * FROM cards;";
		ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
		return extractList(rs);
	}

	@Override
	public Optional<Card> findById(Integer id) {
		String query = "SELECT * FROM cards WHERE id= " + id;
		ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
		return extractObject(rs);

	}

	@Override
	public Optional<Card> findByClient(Client client) {
		String query = "SELECT * FROM cards WHERE client_id =" + client.getId();
		ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
		return extractObject(rs);
	}

	@Override
	public Optional<Card> findByNif(String nif) {
		String query = "SELECT * FROM cards INNER JOIN clients ON cards.client_id = clients.id WHERE clients.nif ="
				+ nif;
		ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
		return extractObject(rs);
	}

	@Override
	public Optional<Card> findByAccount(Account account) {
		String query = "SELECT * FROM cards WHERE account_id=" + account.getId();
		ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
		return extractObject(rs);
	}

	@Override
	public Optional<Card> findByClientAndPin(Client client, Integer pin) {
		String query = "SELECT * FROM cards WHERE client_id =" + client.getId() + " AND pin=" + pin;
		ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
		return extractObject(rs);
	}

	@Override
	public void deleteById(Integer id) {
		String query = "DELETE FROM cards WHERE id=" + id;
		DataBase.executeQuery(query, Operation.DELETE);
	}

	private List<Card> extractList(ResultSet rs) {
		List<Card> cards = new ArrayList<>();
		try {
			while (rs.next()) {
				Card card =  buildObject(rs);
				cards.add(card);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cards;

	}

	private Card buildObject(ResultSet rs) {
		Card card = new Card();
		try {
			Client c = new Client();
			Account account = new Account();
			card.setId(rs.getInt(1));

			c.setId(rs.getInt(2));
			card.setClient(c);

			account.setId(rs.getInt(3));
			card.setAccount(account);
			card.setPin(rs.getString(4));
			
			card.setIsOriginalPin(rs.getShort(7));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return card;

	}
	@Override
	public Account defineAccount(Card card) {
		Account account=new Account();
		Client client=new Client();
		String query = "SELECT accounts.id, nib, accounts.client_primary, accounts.balance FROM cards INNER JOIN accounts ON accounts.id=cards.account_id WHERE cards.id ="+ card.getId()+";";
		ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
		try {
			if(rs.next()) {
				account.setId(rs.getInt(1));
				account.setNib(rs.getInt(2));
				client.setId(rs.getInt(3));
				account.setPrimaryClient(client);
				account.setBalance(rs.getDouble(4));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return account;
	}
	
	@Override
	public Double definePlafond(Card card) {
		double plafond=0.0;
		String query = "SELECT plafond FROM cards WHERE id ="+ card.getId()+";";
		ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
		try {
			if(rs.next()) {
				plafond=rs.getDouble(1);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return plafond;
	}
	
	@Override
	public Double defineDailyWithdrawal(Card card) {
		double dailyWithdrawal=0.0;
		String query = "SELECT daily_withdrawal FROM cards WHERE id ="+ card.getId()+";";
		ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
		try {
			if(rs.next()) {
				dailyWithdrawal=rs.getDouble(1);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dailyWithdrawal;
	}


	private Optional<Card> extractObject(ResultSet rs) {

		try {
			if (rs.next()) {
				Card card =  buildObject(rs);
				return Optional.of(card);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();

	}

	@Override
	public boolean isCreditCard(Integer id) {
		String query = "SELECT * FROM cards WHERE id= " + id;
		ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
		try {

			if (rs.next()) {
				if (rs.getDouble(5) > 0)
					return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int countCreditCardByClient(Card card) {
		String query = "SELECT * FROM cards WHERE plafond NOT LIKE '" + null + "' AND client_id= "
				+ card.getClient().getId() + ";";
		ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
		List<Card> cards = extractList(rs);

		return cards.size();
	}

	@Override
	public int countDebitCardByClient(Card card) {
		String query = "SELECT * FROM cards WHERE daily_withdrawal NOT LIKE '" + null + "' AND client_id= "
				+ card.getClient().getId() + ";";
		ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
		List<Card> cards = extractList(rs);

		return cards.size();
	}

	@Override
	public int countCardsByAccount(Card card) {
		String query = "SELECT * FROM cards WHERE account_id= " + card.getAccount().getId() + ";";
		ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
		List<Card> cards = extractList(rs);

		return cards.size();

	}

}
