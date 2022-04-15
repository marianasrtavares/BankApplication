package pt.mrdb.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import pt.mrbd.exception.CardException;
import pt.mrdb.model.Account;
import pt.mrdb.model.Card;
import pt.mrdb.model.Client;
import pt.mrdb.model.CreditCard;
import pt.mrdb.model.DebitCard;

public class CardServiceImplTest {

	private final CardService cardService = new CardServiceImpl();
	private Card card;

	@Before
	public void init() {
		card = new Card();
		card.setId(1);
	}

	@Test
	public void saveCardWithEmptyClientAndExpectsExceptionMessage() {
		String expectedMessage = "Inexisting client";
		CardException unauthorizedException = assertThrows(CardException.class, () -> cardService.save(card));
		assertEquals(unauthorizedException.getMessage(), expectedMessage);
	}

	@Test
	public void saveCardWithEmptyAccountAndExpectsExceptionMessage() {
		Client client = new Client();
		card.setClient(client);
		String expectedMessage = "Inexisting account";
		CardException unauthorizedException = assertThrows(CardException.class, () -> cardService.save(card));
		assertEquals(unauthorizedException.getMessage(), expectedMessage);
	}

	@Test
	public void saveCardWithOverpastCreditCardsByAccountAndExpectsExceptionMessage() {

		Client client1 = new Client();
		client1.setId(15);
		Client client2 = new Client();
		client2.setId(21);
		Client client3 = new Client();
		client3.setId(31);

		Account account = new Account(null, null, null);
		account.setPrimaryClient(client1);
		account.addSecondaryClient(client2);
		account.addSecondaryClient(client3);

		CreditCard card1 = new CreditCard(client1, account, null);
		cardService.save(card1);
		CreditCard card2 = new CreditCard(client2, account, 150.6);
		cardService.save(card2);
		CreditCard card3 = new CreditCard(client3, account, 150.6);

		String expectedMessage = "Overpast credit cards allowed by account";
		CardException unauthorizedException = assertThrows(CardException.class, () -> cardService.save(card3));
		assertEquals(unauthorizedException.getMessage(), expectedMessage);
	}

	@Test
	public void saveCardOverpastCreditCardsAmountByClient() {

		Client client1 = new Client();
		client1.setId(6);
		Client client2 = new Client();
		client2.setId(7);

		Account account = new Account();

		CreditCard card1 = new CreditCard(client1, account, null);
		cardService.save(card1);
		CreditCard card2 = new CreditCard(client1, account, null);

		String expectedMessage = "Overpast credit cards allowed by client";
		CardException unauthorizedException = assertThrows(CardException.class, () -> cardService.save(card2));
		assertEquals(expectedMessage, unauthorizedException.getMessage());

	}

	@Test
	public void saveCardOverpastDebitCardAmountByClient() {

		Client client1 = new Client();
		client1.setId(8);
		Client client2 = new Client();
		client2.setId(9);

		Account account = new Account(null, null, null);

		DebitCard card1 = new DebitCard(client1, account, null);
		cardService.save(card1);
		DebitCard card2 = new DebitCard(client1, account, null);

		String expectedMessage = "Overpast debit cards allowed by client";
		CardException unauthorizesException = assertThrows(CardException.class, () -> cardService.save(card2));
		assertEquals(expectedMessage, unauthorizesException.getMessage());

	}

}
