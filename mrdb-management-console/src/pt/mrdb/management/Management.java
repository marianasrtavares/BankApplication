package pt.mrdb.management;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

import pt.mrdb.model.Account;
import pt.mrdb.model.Card;
import pt.mrdb.model.Client;
import pt.mrdb.model.CreditCard;
import pt.mrdb.model.DebitCard;
import pt.mrdb.service.AccountService;
import pt.mrdb.service.CardService;
import pt.mrdb.service.ClientService;

public class Management {
	private ClientService clientService;
	private CardService cardService;
	private AccountService accountService;
	private Scanner sc;

	public Management(ClientService clientService, CardService cardService, AccountService accountService) {
		this.clientService = clientService;
		this.cardService = cardService;
		this.accountService = accountService;
		sc = new Scanner(System.in);

	}

	public void managementMenu() {
		System.out.println("Welcome");
		System.out.println("0.Exit");
		System.out.println("1.Manage clients");
		System.out.println("2.Manage acconts");
		System.out.println("3.Manage cards");

	}

	public void startManagementApp() throws Exception {

		int option = 0;

		do {
			managementMenu();
			option = sc.nextInt();

			switch (option) {
			case 1:
				startClientApp();
				break;
			case 2:
				startAccountApp();
				break;
			case 3:
				startCardApp();
				break;
			}

		} while (option != 0);

	}

	public void startClientApp() throws Exception {
		try {
			boolean exit = false;

			do {
				clientMenu();
				int option = sc.nextInt();

				switch (option) {
				case 0:
					exit = true;
					break;
				case 1:
					createClient();
					break;
				case 2:
					updateClient();
					break;
				case 3:
					findClient();
					break;
				case 4:
					removeClient();
					break;
				case 5:
					requestCreditCard();
					break;

				case 6:
					requestDebitCard();
					break;

				case 7:
					cancelCreditCard();
					break;

				case 8:
					cancelDebitCard();
					break;
				}

			} while (!exit);

		} catch (Exception e) {
			if (e instanceof InputMismatchException) {
				System.err.println("Enter a number");
				sc.next();
			} else {
				System.err.println(e.getMessage());
			}
		}
	}

	private void clientMenu() {
		System.out.println("0 Exit");
		System.out.println("1.Create client");
		System.out.println("2.Update client");
		System.out.println("3.Find client");
		System.out.println("4.Remove client");
		System.out.println("5.Request credit card");
		System.out.println("6.Request debit card");
		System.out.println("7.Cancel credit card");
		System.out.println("8.Cancel debit card");
	}

	private void createClient() throws Exception {
		System.out.println("Enter nif ");
		String nif = sc.next();
		sc.nextLine();
		System.out.print("Enter password ");
		String password = sc.nextLine();
		System.out.print("Enter name ");
		String name = sc.nextLine();
		System.out.println("Enter date of birth: yyyy-MM-dd");
		LocalDate dateOfBirth = LocalDate.parse(sc.nextLine());
		System.out.print("Enter phone ");
		String phone = sc.nextLine();
		System.out.print("Enter mobile ");
		String mobile = sc.nextLine();
		System.out.print("Enter email ");
		String email = sc.nextLine();
		System.out.print("Enter occupation ");
		String occupation = sc.nextLine();

		Client client = new Client(nif, password, name, dateOfBirth, phone, mobile, email, occupation);
		clientService.save(client);

	}

	private void updateClient() throws Exception {
		System.out.println(clientService.getAll());
		System.out.println("Enter client id to update");
		int id = sc.nextInt();
		Client client = clientService.getById(id);
		System.out.println("1.Update name");
		System.out.println("2.Update phone");
		System.out.println("3.Update mobile");
		System.out.println("4.Update email");
		System.out.println("5.Update occupation");
		System.out.println("6.Update password");

		int option = sc.nextInt();
		switch (option) {
		case 1:
			System.out.println("Enter new name:");
			sc.nextLine();
			client.setName(sc.nextLine());

			break;

		case 2:
			System.out.println("Enter new phone");
			sc.nextLine();
			client.setPhone(sc.nextLine());
			break;

		case 3:
			System.out.println("Enter new mobile");
			sc.nextLine();
			client.setMobile(sc.nextLine());
			break;
		case 4:
			System.out.println("Enter new email");
			sc.nextLine();
			client.setEmail(sc.nextLine());
			break;

		case 5:
			System.out.println("Enter new occupation");
			sc.nextLine();
			client.setOccupation(sc.nextLine());
			break;
		case 6:
			System.out.println("Enter new password");
			sc.nextLine();
			client.setPassword(sc.nextLine());
			break;

		}
		clientService.save(client);

	}

	private void findClient() throws Exception {
		System.out.println(clientService.getAll());
		System.out.println("Enter client id you want to find");
		int id = sc.nextInt();
		Client client = clientService.getById(id);
		System.out.println("Client found :" + client);

	}

	private void removeClient() throws Exception {
		System.out.println(clientService.getAll());
		System.out.println("Enter client id you want to remove");
		int id = sc.nextInt();
		Client client = clientService.getById(id);
		clientService.deleteById(id);
		System.out.println("Client removed :" + client);

	}

	private void requestCreditCard() throws Exception {
		System.out.println(clientService.getAll());
		System.out.println("Enter id client to request new card");
		int id = sc.nextInt();
		System.out.println("Enter plafond:");
		double plafond = sc.nextDouble();
		CreditCard newCard = new CreditCard(clientService.getById(id),
				accountService.getByClient(clientService.getById(id)), plafond);
		cardService.save(newCard);

//		System.out.print("Enter account id ");
//		int idAccount = sc.nextInt();
//		Account account = accountService.getById(idAccount);

	}

	private void requestDebitCard() throws Exception {
		System.out.println(clientService.getAll());
		System.out.println("Enter id client to request new card");
		int id = sc.nextInt();
		System.out.println("Enter dailyWithdrawal:");
		double dailyWithdrawal = sc.nextDouble();
		DebitCard newCard = new DebitCard(clientService.getById(id),
				accountService.getByClient(clientService.getById(id)), dailyWithdrawal);
		cardService.save(newCard);

	}

	private void cancelCreditCard() throws Exception {
		System.out.println(clientService.getAll());
		System.out.println("Enter id client to cancel card");
		int id = sc.nextInt();
		Card card = cardService.getByClient(clientService.getById(id));
		cardService.deleteById(card.getId());
	}

	private void cancelDebitCard() throws Exception {
		System.out.println(clientService.getAll());
		System.out.println("Enter id client to cancel card");
		int id = sc.nextInt();
		Card card = cardService.getByClient(clientService.getById(id));
		cardService.deleteById(card.getId());
	}

	public void startAccountApp() throws Exception {
		try {
			boolean exit = false;

			do {
				accountMenu();
				int option = sc.nextInt();

				switch (option) {
				case 0:
					exit = true;
					break;
				case 1:
					createAccount();
					break;
				case 2:
					addSecondaryClient();
					break;
				case 3:
					updateAccount();
					break;
				case 4:
					findAccount();
					break;
				case 5:
					removeAccount();
					break;
				case 6:
					consultBalance();
					break;
				case 7:
					consultAllSecondaryClients();
					break;
				case 8:
					consultTransationHistory();
					break;
				}

			} while (!exit);

		} catch (Exception e) {
			if (e instanceof InputMismatchException) {
				System.err.println("Enter a number");
				sc.next();
			} else {
				System.err.println(e.getMessage());
			}
		}
	}

	private void accountMenu() {
		System.out.println("0 Exit");
		System.out.println("1.Create account");
		System.out.println("2.Add secondary client to account");
		System.out.println("3.Update account");
		System.out.println("4.Find account");
		System.out.println("5.Remove account");
		System.out.println("6.Consult balance");
		System.out.println("7.Consult all secondary clients");
		System.out.println("8.Consult transation history");
	}

	private void createAccount() throws Exception {
		System.out.print("Enter nib ");
		Integer nib = sc.nextInt();

		System.out.print("Enter primary client id");
		int id = sc.nextInt();
		Client client = clientService.getById(id);

		System.out.print("Enter balance ");
		Double balance = sc.nextDouble();

		Account account = new Account(nib, client, balance);

		System.out.print("Enter how many secondary clients");
		int numberSecondaryClients = sc.nextInt();
		for (int i = 0; i < numberSecondaryClients; i++) {
			System.out.println("Enter id of the new secondary client");
			int idSecondClient = sc.nextInt();
			System.out.println(idSecondClient);
			Client secondaryClient = clientService.getById(idSecondClient);
			System.out.println(secondaryClient);
			account.addSecondaryClient(secondaryClient);
		}

		accountService.save(account);

	}

	private void addSecondaryClient() {
		System.out.print("Enter client id you want to add");
		int id = sc.nextInt();
		Client secondaryClient = clientService.getById(id);
		System.out.print("Enter account id to add the client");
		int idAccount = sc.nextInt();
		Account account = accountService.getById(idAccount);
		account.setSecondaryClient(accountService.findAllSecondaryClients(account));
		account.getSecondaryClient().add(secondaryClient);

		accountService.save(account);

	}

	private void consultAllSecondaryClients() {
		System.out.println("Enter account id to consult all secondary clients");
		int id = sc.nextInt();
		Account account = accountService.getById(id);
		for (Client client : accountService.findAllSecondaryClients(account)) {
			System.out.println(client);
		}
	}

	private void updateAccount() throws Exception {
		System.out.println(accountService.getAll());
		System.out.println("Enter account id to update");
		int id = sc.nextInt();
		Account account = accountService.getById(id);
		System.out.println("1. Update nib");
		System.out.println("2. Update primaryClient");
		System.out.println("3. Update secondaryClient");

		int option = sc.nextInt();
		switch (option) {
		case 1:
			System.out.println("Enter new nib:");
			account.setNib(sc.nextInt());
			break;

		case 2:
			System.out.print("Enter primary client id");
			int idUpdateClient = sc.nextInt();
			Client client = clientService.getById(idUpdateClient);
			account.setPrimaryClient(client);
			break;

		case 3:
			System.out.println("Enter new secondary client id");
			int newClient = sc.nextInt();
			System.out.println("Enter client you want to remove");
			int oldClient = sc.nextInt();
			Set<Client> clients = accountService.findAllSecondaryClients(account);

			clients.remove(clientService.getById(oldClient));
			clients.add(clientService.getById(newClient));
			account.setSecondaryClient(clients);
			break;
		}
		accountService.save(account);

	}

	private void findAccount() throws Exception {
		System.out.println(accountService.getAll());
		System.out.println("Enter account id you want to find");
		int id = sc.nextInt();
		Account account = accountService.getById(id);
		System.out.println("Account found :" + account);

	}

	private void removeAccount() throws Exception {
		System.out.println(accountService.getAll());
		System.out.println("Enter account id you want to remove");
		int id = sc.nextInt();
		Account account = accountService.getById(id);
		accountService.deleteById(id);
		System.out.println("Account removed :" + account);

	}

	private void consultBalance() throws Exception {
		System.out.println(accountService.getAll());
		System.out.println("Enter account id you want to check the balance");
		int id = sc.nextInt();
		Account account = accountService.getById(id);
		double balance = account.getBalance();
		System.out.println("Balance is " + balance);
	}

	private void consultTransationHistory() throws Exception {
		System.out.println(accountService.getAll());
		System.out.println("Enter account id you want to check the history transation");
		int id = sc.nextInt();
		Account account = accountService.getById(id);
		System.out.println("Transations history " + accountService.findAccountTransations(account));

	}

	public void startCardApp() throws Exception {
		try {
			boolean exit = false;

			do {
				cardMenu();
				int option = sc.nextInt();

				switch (option) {
				case 0:
					exit = true;
					break;
				case 1:
					createCard();
					break;
				case 2:
					updateCard();
					break;
				case 3:
					findCard();
					break;
				case 4:
					removeCard();
					break;
				}
			} while (!exit);

		} catch (Exception e) {
			if (e instanceof InputMismatchException) {
				System.err.println("Enter a number");
				sc.next();
			} else {
				System.err.println(e.getMessage());
			}
		}
	}

	private void cardMenu() {
		System.out.println("0.Exit");
		System.out.println("1.Create card");
		System.out.println("2.Update card");
		System.out.println("3.Find card");
		System.out.println("4.Cancel card");
	}

	private void createCard() throws Exception {
		System.out.println("Enter option: ");
		System.out.println("1.Credit card");
		System.out.println("2.Debit card");

		int option = sc.nextInt();

		System.out.print("Enter client owner id");
		int id = sc.nextInt();
		Client client = clientService.getById(id);

		System.out.print("Enter account id ");
		int idAccount = sc.nextInt();
		Account account = accountService.getById(idAccount);

		if (option == 1) {
			System.out.println("Enter plafond");
			double plafond = sc.nextDouble();
			CreditCard card = new CreditCard(client, account, plafond);
			cardService.save(card);
		} else {

			System.out.println("Enter dailyWithdrawal");
			double dailyWithdrawal = sc.nextDouble();
			DebitCard card = new DebitCard(client, account, dailyWithdrawal);
			cardService.save(card);
		}
	}

	private void updateCard() throws Exception {
		System.out.println(cardService.getAll());
		System.out.println("Enter card id you want to update");
		int id = sc.nextInt();
		Card card = cardService.getById(id);
		System.out.println("Enter new pin");
		String pin = sc.next();
		card.setPin(pin);

		cardService.save(card);

	}

	private void findCard() throws Exception {
		for (Card c : cardService.getAll()) {
			System.out.println(c.getId());
		}
		System.out.println("Enter card id you want to find");
		int id = sc.nextInt();
		Card card = cardService.getById(id);
		System.out.println("Card found : " + card);

	}

	private void removeCard() throws Exception {
		System.out.println(cardService.getAll());
		System.out.println("Enter card id you want to remove");
		int id = sc.nextInt();
		Card card = cardService.getById(id);
		cardService.deleteById(id);
		System.out.println("Card removed :" + card);

	}

}
