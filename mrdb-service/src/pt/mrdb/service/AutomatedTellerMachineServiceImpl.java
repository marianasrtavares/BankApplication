package pt.mrdb.service;

import pt.mrbd.exception.FundException;
import pt.mrdb.model.Account;
import pt.mrdb.model.Card;
import pt.mrdb.model.CreditCard;

public class AutomatedTellerMachineServiceImpl implements AutomatedTellerMachineService {
	private CardService cardService;
	private AccountService accountService;



	public AutomatedTellerMachineServiceImpl(CardService cardService, AccountService accountService) {
		this.cardService = cardService;
		this.accountService = accountService;

	}	

	@Override
	public void withdraw(Account account, Double amount) throws FundException {
		 

			if (account.getBalance() <= 0) {
				throw new FundException("Cannot withdraw specified amount");
			}
			if (amount < 0) {
				throw new FundException("Cannot withdraw negative amount");
			}
			if (account.getBalance() < amount) {
				throw new FundException("Insufficient funds");
			}
			if (amount > 400) {
				throw new FundException("Amount withdraw exceeded");
			}
			account.setBalance(account.getBalance() - amount);
			accountService.save(account);
			cardService.save(cardService.getByAccount(account));
		
	}

	@Override
	public void cashAdvance(Card cardActive, Double amount) throws FundException {
		 

			if (cardActive instanceof CreditCard) {
				CreditCard card = (CreditCard) cardActive;
				if (card.getPlafond() < amount)
					throw new FundException("Insufficient funds");
				if (amount > 200)
					throw new FundException("Amount exceeded");
				if (amount < 0) {
					throw new FundException("Negative amount value");
				}
				card.setPlafond(card.getPlafond() - amount);
				cardActive = card;
				cardService.save(cardActive);

			}
		
	}

	@Override
	public void deposit(Account account, Double amount) throws FundException {
		 
			if (amount < 0) {
				throw new FundException("Cannot deposit negative amount");
			}
			account.setBalance(account.getBalance() + amount);
			accountService.save(account);
			cardService.save(cardService.getByAccount(account));
		
	}

	@Override
	public void transference(Account owner, Account receiver, Double amount) throws FundException {
		 

			if (amount > 200) {
				throw new FundException("Amount transference exceeded");
			}
			if (amount < 0) {
				throw new FundException("Cannot tranfere negative amount");
			}

			withdraw(owner, amount);
			deposit(receiver, amount);
		
	}
}
