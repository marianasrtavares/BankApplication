package pt.mrdb;

import java.util.Scanner;

import pt.mrdb.atm.AutomatedTellerMachine;
import pt.mrdb.management.Management;
import pt.mrdb.service.AccountService;
import pt.mrdb.service.AccountServiceImpl;
import pt.mrdb.service.CardService;
import pt.mrdb.service.CardServiceImpl;
import pt.mrdb.service.ClientService;
import pt.mrdb.service.ClientServiceImpl;

public class App {

	private static AutomatedTellerMachine atm;
	private static Management management;
	private static Scanner sc;

	public static void main(String[] args) {
		ClientService clientService = new ClientServiceImpl();
		CardService cardService = new CardServiceImpl();
		AccountService accountService = new AccountServiceImpl();
		atm = new AutomatedTellerMachine(cardService, accountService);
		management = new Management(clientService, cardService, accountService);
		sc = new Scanner(System.in);

		
		try {
			System.out.println("Welcome!");
			int option = 0;
			do {
				principalMenu();
				option = sc.nextInt();
				switch (option) {
				case 1:
					management.startManagementApp();
					break;
				case 2:
					atm.startATM();
					break;
				}
			} while (option != 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void principalMenu() {
		System.out.println("Choose:");
		System.out.println("0.Exit");
		System.out.println("1.Management");
		System.out.println("2.ATM");
	}

}
