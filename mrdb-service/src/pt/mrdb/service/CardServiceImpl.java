package pt.mrdb.service;

import java.util.List;

import pt.mrbd.exception.CardException;
import pt.mrdb.model.Account;
import pt.mrdb.model.Card;
import pt.mrdb.model.Client;
import pt.mrdb.model.CreditCard;
import pt.mrdb.model.DebitCard;
import pt.mrdb.repository.CardRepository;
import pt.mrdb.repository.CardRepositoryImpl;

public class CardServiceImpl implements CardService {

	CardRepository cardRepository = new CardRepositoryImpl();

	@Override
	public Card save(Card card) {

			if (card != null && card.getId() == null) {
				int countCardByAccounts = cardRepository.countCardsByAccount(card);
				int countCreditCardByClient = cardRepository.countCreditCardByClient(card);
				int countDebitCardByClient = cardRepository.countDebitCardByClient(card);
				
				if (card.getClient() == null) {
					throw new CardException("Inexisting client");
					
				}if (card.getAccount() == null) {
					throw new CardException("Inexisting account");
				}
				 else {
					if (card instanceof CreditCard) {
						if (countCreditCardByClient >=1) {
							throw new CardException("Overpast credit cards allowed by client");
						}
						if (countCardByAccounts >= 2) {
							throw new CardException("Overpast credit cards allowed by account");
						}
						CreditCard credit = (CreditCard) card;
						return cardRepository.save(credit).get();
						
					}
					if (countDebitCardByClient >=1) {
						throw new CardException("Overpast debit cards allowed by client");
					}
					DebitCard debit = (DebitCard) card;
					return cardRepository.save(debit).get();
				
				}
			} else {
				
					return cardRepository.save(card).get();
				
				
			}
	}


	@Override
	public List<Card> getAll() {
		return cardRepository.findAll();
	}

	@Override
	public Card getById(Integer id) throws CardException {
			Card card = null;
			Card cardRepo= cardRepository.findById(id).get();
			if (cardRepository.isCreditCard(cardRepo.getId())) {
				card = new CreditCard();
			} else {
				card = new DebitCard();
			}

			card.setId(cardRepo.getId());
			card.setClient(cardRepo.getClient());
			card.setAccount(cardRepository.defineAccount(cardRepo));
			card.setPin(cardRepo.getPin());
			card.setIsOriginalPin(cardRepo.getIsOriginalPin());
			
			if(card instanceof CreditCard) {
				CreditCard creditCard= (CreditCard)card;
				creditCard.setPlafond(cardRepository.definePlafond(cardRepo));
				return creditCard;
			}else {
				DebitCard debitCard= (DebitCard)card;
				debitCard.setDailyWithdrawal(cardRepository.defineDailyWithdrawal(cardRepo));
				return debitCard;
			}

	}

	@Override
	public Card getByClient(Client client) throws CardException {
		return cardRepository.findByClient(client).get();

	}

	@Override
	public Card getByNif(String nif) {
		return cardRepository.findByNif(nif).get();
	}

	@Override
	public Card getByAccount(Account account) {
		Card card = null;
		Card cardRepo= cardRepository.findByAccount(account).get();
		if (cardRepository.isCreditCard(cardRepo.getId())) {
			card = new CreditCard();

		} else {
			card = new DebitCard();
		}

		card.setId(cardRepo.getId());
		card.setClient(cardRepo.getClient());
		card.setAccount(cardRepo.getAccount());
		card.setPin(cardRepo.getPin());
		card.setIsOriginalPin(cardRepo.getIsOriginalPin());
		
		if(card instanceof CreditCard) {
			CreditCard creditCard= (CreditCard)card;
			creditCard.setPlafond(cardRepository.definePlafond(cardRepo));
			return creditCard;
		}else {
			DebitCard debitCard= (DebitCard)card;
			debitCard.setDailyWithdrawal(cardRepository.defineDailyWithdrawal(cardRepo));
			return debitCard;
		}

		
	}

	@Override
	public Card getByClientAndPin(Client client, Integer pin) {
		return cardRepository.findByClientAndPin(client,pin).get();
	}

	@Override
	public void deleteById(Integer id) {
		cardRepository.deleteById(id);
	}

	@Override
	public Boolean login(Integer cardNnumber, String pin) {
		
		return cardRepository.findById(cardNnumber).get().getPin().equals(pin);

	}

}
