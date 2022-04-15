package pt.mrdb.atm;

import java.util.Scanner;

import pt.mrdb.model.Account;
import pt.mrdb.model.Card;
import pt.mrdb.model.CreditCard;
import pt.mrdb.service.AccountService;
import pt.mrdb.service.AutomatedTellerMachineService;
import pt.mrdb.service.AutomatedTellerMachineServiceImpl;
import pt.mrdb.service.CardService;

public class AutomatedTellerMachine {
	private CardService cardService;
	private AccountService accountService;
	private AutomatedTellerMachineService automatedTellerMachine;
	private Scanner sc;

	public AutomatedTellerMachine(CardService cardService, AccountService accountService) {
		automatedTellerMachine = new AutomatedTellerMachineServiceImpl(cardService, accountService);
		this.cardService = cardService;
		this.accountService = accountService;
		sc = new Scanner(System.in);

	}

	public void startATM() throws Exception {
		try {

			Card cardActive = login();
			if (cardActive != null) {
				if (!hasCardAlreadyChangedPin(cardActive)) {
					redefinePin(cardActive);

				}
				int option = 0;
				do {
					
					ATMMenu(cardActive);
					option = sc.nextInt();

					switch (option) {
					case 1:
						transfere(cardActive);
						break;
					case 2:
						withdraw(cardActive);
						break;
					case 3:
						deposit(cardActive);
						break;
					case 5:
						cashAdvance(cardActive);
						break;
					}

				} while (option != 0);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	public void redefinePin(Card card) throws Exception {
		try {
			System.out.println("Enter new 4 digits pin");
			String newPin = sc.next();
			card.setPin(newPin);
			Short s = 0;
			card.setIsOriginalPin(s);
			cardService.save(card);
		} catch (Exception e) {
			e.printStackTrace();
			}

		}
	

	public boolean hasCardAlreadyChangedPin(Card cardActive) {
		return cardActive.getIsOriginalPin() == 0;
	}

	public Card login() throws Exception {
		try {
			System.out.println("Login");
			System.out.println("Enter card number");
			int cardNumber = sc.nextInt();

			System.out.println("Enter password");
			String password = sc.next();
			if (!cardService.login(cardNumber, password))
				throw new Exception("Error login");
			
					return cardService.getById(cardNumber);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void ATMMenu(Card cardActive) {
		System.out.println("Welcome");
		System.out.println("0.Exit");
		System.out.println("1.Transfere");
		System.out.println("2.Withdraw");
		System.out.println("3.Deposit");
		if (cardActive instanceof CreditCard) {
			System.out.println("4.CashAdvance");
		}

	}

	public void withdraw(Card cardActive) throws Exception {
		System.out.println("Enter amount: ");
		double amount = sc.nextDouble();
		automatedTellerMachine.withdraw(accountService.getById(cardActive.getAccount().getId()), amount);
	}
	
	public void deposit(Card cardActive) {
		System.out.println("Enter amount: ");
		double amount = sc.nextDouble();
		automatedTellerMachine.deposit(accountService.getById(cardActive.getAccount().getId()), amount);
		
	}

	public void cashAdvance(Card cardActive) throws Exception {
		System.out.println("Enter amount: ");
		double amount = sc.nextDouble();
		automatedTellerMachine.cashAdvance(cardActive, amount);
	}

	public void transfere(Card cardActive) throws Exception {
		System.out.println("Enter id account you want to transfere: ");
		int id = sc.nextInt();
		System.out.println("Enter amout: ");
		double amount = sc.nextDouble();
		Account accountReceiver = accountService.getById(id);
		automatedTellerMachine.transference(cardActive.getAccount(), accountReceiver, amount);

	}

}
