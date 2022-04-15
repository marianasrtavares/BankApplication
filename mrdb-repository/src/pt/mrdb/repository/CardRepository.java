package pt.mrdb.repository;

import java.util.List;
import java.util.Optional;

import pt.mrdb.model.Account;
import pt.mrdb.model.Card;
import pt.mrdb.model.Client;

public interface CardRepository {

	Optional<Card> save(Card card); // create and update

	List<Card> findAll();

	Optional<Card> findById(Integer id);

	Optional<Card> findByClient(Client client);

	Optional<Card> findByNif(String nif);

	Optional<Card> findByAccount(Account account);

	Optional<Card> findByClientAndPin(Client client, Integer pin);

	void deleteById(Integer id);

	int countCreditCardByClient(Card card);

	int countDebitCardByClient(Card card);

	int countCardsByAccount(Card card);

	boolean isCreditCard(Integer id);

	Account defineAccount(Card card);

	Double definePlafond(Card card);

	Double defineDailyWithdrawal(Card cardRepo);

}
