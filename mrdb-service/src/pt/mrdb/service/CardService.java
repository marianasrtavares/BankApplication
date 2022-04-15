package pt.mrdb.service;

import java.util.List;

import pt.mrbd.exception.CardException;
import pt.mrdb.model.Account;
import pt.mrdb.model.Card;
import pt.mrdb.model.Client;

public interface CardService {

	Card save(Card card) throws CardException; // create and update

	List<Card> getAll();

	Card getById(Integer id) throws CardException;

	Card getByClient(Client client) throws CardException;

	Card getByNif(String nif);

	Card getByAccount(Account account) throws CardException;

	Card getByClientAndPin(Client client, Integer pin);

	void deleteById(Integer id) throws CardException;

	Boolean login(Integer cardNnumber, String pin) throws CardException;

}
