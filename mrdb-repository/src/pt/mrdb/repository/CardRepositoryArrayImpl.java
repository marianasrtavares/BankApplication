//package pt.mrdb.repository;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import pt.mrdb.model.Account;
//import pt.mrdb.model.Card;
//import pt.mrdb.model.Client;
//
//public class CardRepositoryArrayImpl implements CardRepository {
//
//	private static int id=1000;
//	private Card[] cards = new Card[3];
//
//	@Override
//	public Optional<Card> save(Card card) {
//	
//		for (int i = 0; i < cards.length; i++) {
//			if (cards[i] == null) {
//				cards[i] = card;
//				cards[i].setId(id);
//				id++;
//				return Optional.of(cards[i]);
//			}
//			if (cards[i].getId().equals(card.getId())) {
//				cards[i] = card;
//				return Optional.of(cards[i]);
//			}
//		}
//
//		throw new RuntimeException("Database is full");
//	}
//
//	@Override
//	public List<Card> findAll() {
//		return Arrays.asList(cards);
//	}
//
//	@Override
//	public Optional<Card> findById(Integer id) {
//		for (int i = 0; i < cards.length; i++) {
//			if (cards[i] == null)
//				continue;
//			if (cards[i].getId().equals(id)) {
//				return Optional.of(cards[i]);
//			}
//		}
//		throw new RuntimeException("Card id " + id + " not found");
//	}
//
//	@Override
//	public Optional<Card> findByClient(Client client) {
//		for (int i = 0; i < cards.length; i++) {
//			if (cards[i] == null)
//				continue;
//			if (cards[i].getClient().equals(client)) {
//				return Optional.of(cards[i]);
//			}
//		}
//		throw new RuntimeException("Cards client " + client + " not found");
//	}
//
//	@Override
//	public Optional<Card> findByNif(String nif) {
//		for (int i = 0; i < cards.length; i++) {
//			if (cards[i] == null)
//				continue;
//			if (cards[i].getClient().getNif().equals(nif)) {
//				return Optional.of(cards[i]);
//			}
//		}
//		throw new RuntimeException("Cards client nif " + nif + " not found");
//
//	}
//
//	@Override
//	public Optional<Card> findByAccount(Account account) {
//		for (int i = 0; i < cards.length; i++) {
//			if (cards[i] == null)
//				continue;
//			if (cards[i].getAccount().equals(account)) {
//				return Optional.of(cards[i]);
//			}
//		}
//		throw new RuntimeException("Card of the account " + account + " not found");
//
//	}
//
//	@Override
//	public Optional<Card> findByClientAndPin(Client client, Integer pin){
//		for (int i = 0; i < cards.length; i++) {
//			if (cards[i] == null)
//				continue;
//			if (cards[i].getClient().equals(client) && cards[i].getPin().equals(pin)) {
//				return Optional.of(cards[i]);
//			}
//		}
//		throw new RuntimeException("Card of client  " + client + " with pin " + pin + " not found");
//
//	}
//
//	@Override
//	public void deleteById(Integer id) {
//		for (int i = 0; i < cards.length; i++) {
//			if (cards[i] == null)
//				continue;
//			if (cards[i].getId().equals(id)) {
//				cards[i] = null;
//			}
//		}
//	}
//}
