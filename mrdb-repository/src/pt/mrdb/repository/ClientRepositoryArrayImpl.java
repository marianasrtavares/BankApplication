//package pt.mrdb.repository;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import pt.mrdb.model.Client;
//
//public class ClientRepositoryArrayImpl implements ClientRepository {
//
//	private static int id;
//	private Client[] clients = new Client[3];
//
//	@Override
//	public Optional<Client> save(Client client) {
//
//		for (int i = 0; i < clients.length; i++) {
//
//			if (clients[i] == null) {
//				checkUniqueNif(client.getNif());
//				clients[i] = client;
//				clients[i].setId(id);
//				id++;
//				return Optional.of(clients[i]);
//			} else {
//				if (clients[i].getId().equals(client.getId())) {
//					clients[i] = client;
//					return Optional.of(clients[i]);
//				}
//			}
//		}
//		throw new RuntimeException("Database is full");
//	}
//
//	private void checkUniqueNif(String nif) {
//		for (int i = 0; i < clients.length; i++) {
//			if (clients[i] == null)
//				continue;
//			if (clients[i].getNif().equals(nif)) {
//				throw new RuntimeException("Client nif already exixts");
//			}
//		}
//	}
//
//	@Override
//	public List<Client> findAll() {
//		return Arrays.asList(clients);
//	}
//
//	@Override
//	public Optional<Client> findById(Integer id) {
//		for (int i = 0; i < clients.length; i++) {
//			if (clients[i] == null)
//				continue;
//			if (clients[i].getId().equals(id)) {
//				return Optional.of(clients[i]);
//			}
//		}
//		throw new RuntimeException("Client id " + id + " not found");
//	}
//
//	@Override
//	public Optional<Client> findByNib(String nif){
//		for (int i = 0; i < clients.length; i++) {
//			if (clients[i] == null)
//				continue;
//			if (clients[i].getNif().equals(nif)) {
//				return Optional.of(clients[i]);
//			}
//		}
//		throw new RuntimeException("Client nif " + nif + " not found");
//	}
//
//	@Override
//	public void deleteById(Integer id)  {
//			for (int i = 0; i < clients.length; i++) {
//				if (clients[i] == null)
//					continue;
//				if (clients[i].getId().equals(id)) {
//					clients[i] = null;
//				}
//			}
//		}
//	}
//
