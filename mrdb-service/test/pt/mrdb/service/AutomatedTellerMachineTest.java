package pt.mrdb.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import pt.mrbd.exception.FundException;
import pt.mrdb.model.Account;
import pt.mrdb.model.CreditCard;

public class AutomatedTellerMachineTest {

	private AccountService accountService;
	private CardService cardService;
	private AutomatedTellerMachineService automatedTellerMachine;

	@Before
	public void init () {
		this.automatedTellerMachine = new AutomatedTellerMachineServiceImpl(cardService,
				accountService);
	}
	
	@Test
	public void withdraWithAmountBellowOrEqualsToZeroAndExpectsExceptionMessage() {
		Account account = new Account(null, null, null);
		double amount = 0;
		String expectedMessage = "Cannot withdraw negative or null amount";
		FundException unauthorizedException = assertThrows(FundException.class,
				() -> automatedTellerMachine.withdraw(account, amount));
		assertEquals(unauthorizedException.getMessage(), expectedMessage);
	}

	@Test
	public void withdraWithAmountOverFourHundredAndExpectsExceptionMessage() {
		Account account = new Account(null, null, null);
		double amount = 401;
		String expectedMessage = "Amount withdraw exceeded";
		FundException unauthorizedException = assertThrows(FundException.class,
				() -> automatedTellerMachine.withdraw(account, amount));
		assertEquals(unauthorizedException.getMessage(), expectedMessage);
	}

	@Test
	public void withdraWithBalanceBellowOrEqualsToZeroAndExpectsExceptionMessage() {
		Account account = new Account(null, null, null);
		account.setBalance(0.0);
		double amount = 3;
		String expectedMessage = "Cannot withdraw specific amount";
		FundException unauthorizedException = assertThrows(FundException.class,
				() -> automatedTellerMachine.withdraw(account, amount));
		assertEquals(unauthorizedException.getMessage(), expectedMessage);

	}

	@Test
	public void withdraWithBalanceBellowAmountAndExpectsExceptionMessage() {
		Account account = new Account(null, null, null);
		account.setBalance(23.0);
		double amount = 121;
		String expectedMessage = "Insufficient funds";
		FundException unauthorizedException = assertThrows(FundException.class,
				() -> automatedTellerMachine.withdraw(account, amount));
		assertEquals(unauthorizedException.getMessage(), expectedMessage);

	}

	@Test
	public void cashAadvanceWithPlafondAboveAmountAndExpectsExceptionMessage() {
		CreditCard card = new CreditCard(null, null, 190.0);
		double amount = 200;
		String expectedMessage = "Insufficient funds";
		FundException unauthorizesException = assertThrows(FundException.class,
				() -> automatedTellerMachine.cashAdvance(card, amount));
		assertEquals(unauthorizesException.getMessage(), expectedMessage);
	}

	@Test
	public void cashAadvanceWithAmountOverTwoHundredAndExpectsExceptionMessage() {
		CreditCard card = new CreditCard(null, null, 300.0);
		double amount = 201;
		String expectedMessage = "Amount exceeded";
		FundException unauthorizesException = assertThrows(FundException.class,
				() -> automatedTellerMachine.cashAdvance(card, amount));
		assertEquals(unauthorizesException.getMessage(), expectedMessage);
	}

	@Test
	public void cashAadvanceWithAmountBellowOrEqualsToZeroAndExpectsExceptionMessage() {
		CreditCard card = new CreditCard(null, null, 190.0);
		double amount = 0;
		String expectedMessage = "Negative or null amount value";
		FundException unauthorizesException = assertThrows(FundException.class,
				() -> automatedTellerMachine.cashAdvance(card, amount));
		assertEquals(unauthorizesException.getMessage(), expectedMessage);
	}

	@Test
	public void depositWithAmountBellowOrEqualsToZeroAndExpectsExceptionMessage() {
		Account account = new Account(null, null, null);
		double amount = 0;
		String expectedMessage = "Cannot deposit negative or null amount";
		FundException unauthorizedException = assertThrows(FundException.class,
				() -> automatedTellerMachine.deposit(account, amount));
		;
		assertEquals(unauthorizedException.getMessage(), expectedMessage);

	}

	@Test
	public void transferenceWithAmountOverTwoHundredAndExpectsExceptionMessage() {
		Account accountOwner = new Account(null, null, null);
		Account accountReceiver = new Account(null, null, null);
		double amount = 201;
		String expectedMessage = "Amount transference exceeded";
		FundException unauthorizedException = assertThrows(FundException.class,
				() -> automatedTellerMachine.transference(accountOwner, accountReceiver, amount));
		;
		assertEquals(unauthorizedException.getMessage(), expectedMessage);

	}

	@Test
	public void transferenceWithAmountBellowOrEqualsToZeroAndExpectsExceptionMessage() {
		Account accountOwner = new Account(null, null, null);
		Account accountReceiver = new Account(null, null, null);
		double amount = 0;
		String expectedMessage = "Cannot tranfere negative or null amount";
		FundException unauthorizedException = assertThrows(FundException.class,
				() -> automatedTellerMachine.transference(accountOwner, accountReceiver, amount));
		;
		assertEquals(unauthorizedException.getMessage(), expectedMessage);

	}
	
	@Test
	public void transferenceWithBalanceBellowAmountAndExpectsExceptionMessage() {
		Account accountOwner = new Account(null, null, 20.0);
		Account accountReceiver = new Account(null, null, null);
		double amount = 30;
		String expectedMessage = "Insufficient funds";
		FundException unauthorizedException = assertThrows(FundException.class,
				() -> automatedTellerMachine.transference(accountOwner, accountReceiver, amount));
		;
		assertEquals(unauthorizedException.getMessage(), expectedMessage);

	}
	
	

}
