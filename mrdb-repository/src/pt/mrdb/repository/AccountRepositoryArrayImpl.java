//package pt.mrdb.repository;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import pt.mrdb.model.Account;
//import pt.mrdb.model.Client;
//
//public class AccountRepositoryArrayImpl implements AccountRepository {
//
//	private Account[] accounts = new Account[3]; // criar array automatico
//	private static int id;
//
//	@Override
//	public Optional<Account> save(Account account) {
//		if(account.getId()==null) {
//		checkUniqueNib(account);
//		}       
//        
//		for (int i = 0; i < accounts.length; i++) {
//			if (accounts[i] == null) {
//				account.setId(id);
//				id++;
//				accounts[i] = account;
//				return Optional.of(accounts[i]);
//
//			} else if (accounts[i].getId().equals(account.getId())) {
//				accounts[i] = account;
//				return Optional.of(accounts[i]);
//			}
//		}
//		throw new RuntimeException("Database is Full");
//
//	}
//
//	@Override
//	public List<Account> findAll() {
//		return Arrays.asList(accounts);
//	}
//
//	@Override
//	public Optional<Account> findById(Integer id) {
//		for (int i = 0; i < accounts.length; i++) {
//			if (accounts[i] == null) {
//				continue;
//			}
//			if (accounts[i].getId().equals(id)) {
//				return Optional.of(accounts[i]);
//			}
//		}
//		throw new RuntimeException("Account id " + id + " not found");
//
//	}
//
//	@Override
//	public Optional<Account> findByNib(Integer nib) {
//		for (int i = 0; i < accounts.length; i++) {
//			if (accounts[i] == null) {
//				continue;
//			}
//			if (accounts[i].getNib().equals(nib)) {
//				return Optional.of(accounts[i]);
//			}
//		}
//		throw new RuntimeException("Account nib " + nib + "not found");
//
//	}
//	
//	@Override
//	public Optional<Account> findByClient(Client client)  {
//		for (int i = 0; i < accounts.length; i++) {
//			if (accounts[i] == null) {
//				continue;
//			}
//			if (accounts[i].getPrimaryClient().equals(client)) {
//				return Optional.of(accounts[i]);
//			}
//			
//			if(accounts[i].getSecondaryClient()!=null) {
//			for(Client secondaryClient: accounts[i].getSecondaryClient()) {
//				if(secondaryClient==null) continue;
//				if(secondaryClient.equals(client)) {
//					return Optional.of(accounts[i]);
//				}
//			}
//		}
//		}
//		throw new RuntimeException("Account of client " + client + "not found");
//	}
//	
//	public int countAccountByClient(Client client) {
//		int count=0;
//		for (int i = 0; i < accounts.length; i++) {
//			if (accounts[i] == null) {
//				continue;
//			}
//			if (accounts[i].getPrimaryClient().equals(client)) {
//				++count;
//			}
//			if(accounts[i].getSecondaryClient()!=null) {
//			for(Client secondaryClient: accounts[i].getSecondaryClient()) {
//				if(secondaryClient==null) continue;
//				if(secondaryClient.equals(client)) {
//					++count;
//				}
//			}
//		}
//		}
//		return count;
//	}
//
//	
//	@Override
//	public void deleteById(Integer id)  {
//		for (int i = 0; i < accounts.length; i++) {
//			if (accounts[i] == null)
//				continue;
//			if (accounts[i].getId().equals(id)) {
//				accounts[i] = null;
//			}
//		}
//	}
//
//	private void checkUniqueNib(Account account)  {
//		for (int i = 0; i < accounts.length; i++) {
//			if (accounts[i] == null)
//				continue;
//			if (accounts[i].getNib().equals(account.getNib())) {
//				throw new RuntimeException("Nib already exists.");
//
//			}
//		}
//	}
//
//	
//}
