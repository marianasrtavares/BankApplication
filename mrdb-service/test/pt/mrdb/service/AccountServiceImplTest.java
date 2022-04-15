package pt.mrdb.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import pt.mrbd.exception.AccountException;
import pt.mrdb.model.Account;
import pt.mrdb.model.Client;

public class AccountServiceImplTest {

	private final AccountService accountService = new AccountServiceImpl();
	private Account account;

	@Before
	public void init() {
		account = new Account(null, null, null);
	}

	@Test
	public void saveAccountWithEmptyPrimaryClientAndExpectsExceptionMessage() {
		String expectedMessage = "Account must have primary client";
		AccountException unauthorizedException = assertThrows(AccountException.class,
				() -> accountService.save(account));
		assertEquals(unauthorizedException.getMessage(), expectedMessage);
	}

	@Test
	public void saveAccountWithPrimaryClientAgeBellowEighteeAndExpectsExceptionMessage() {
		Client client = new Client();
		LocalDate dateOfBirth = LocalDate.of(2020, 01, 01);
		client.setDateOfBirth(dateOfBirth);
		account.setPrimaryClient(client);
		String expectedMessage = "Client age is not over 18";
		AccountException unauthorizedException = assertThrows(AccountException.class,
				() -> accountService.save(account));
		assertEquals(unauthorizedException.getMessage(), expectedMessage);
	}

	@Test
	public void saveAccountWithNumberOfSecondaryClientAndExpectsExceptionMessage() {
		Client principalClient = new Client();
		LocalDate dateOfBirth= LocalDate.of(2000,01,01);
		principalClient.setDateOfBirth(dateOfBirth);
		account.setPrimaryClient(principalClient);
		account.setBalance(234.5);
		Client secondaryClient1 = new Client();		secondaryClient1.setId(1);		account.addSecondaryClient(secondaryClient1);
		Client secondaryClient2 = new Client();		secondaryClient2.setId(2);		account.addSecondaryClient(secondaryClient2);
		Client secondaryClient3 = new Client();		secondaryClient3.setId(3);		account.addSecondaryClient(secondaryClient3);
		Client secondaryClient4 = new Client();		secondaryClient4.setId(4);		account.addSecondaryClient(secondaryClient4);
		Client secondaryClient5 = new Client();		secondaryClient5.setId(5);		account.addSecondaryClient(secondaryClient5);
		System.out.println(account.getSecondaryClient().size());
		String expectedMessage = "Overpast number of secondary clients";
		AccountException unauthorizedException = assertThrows(AccountException.class,
				() -> accountService.save(account));
		assertEquals(unauthorizedException.getMessage(), expectedMessage);
	}
	
	@Test
	public void saveAccountWithBelowMinimMandatoryBalanceAndExpectsExceptionMessage() {
		Client client = new Client();
		LocalDate dateOfBirth = LocalDate.of(2000, 01, 01);
		client.setDateOfBirth(dateOfBirth);
		account.setPrimaryClient(client);
		account.setBalance(23.0);
		String expectedMessage = "Below the minimum mandatory balance of 50€";
		AccountException unauthorizedException = assertThrows(AccountException.class,
				() -> accountService.save(account));
		assertEquals(unauthorizedException.getMessage(), expectedMessage);
	}

}
