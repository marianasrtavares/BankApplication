package pt.mrdb.service;

import java.util.List;
import java.util.Set;

import pt.mrbd.exception.ClientException;
import pt.mrdb.model.Account;
import pt.mrdb.model.Client;
import pt.mrdb.repository.ClientRepository;
import pt.mrdb.repository.ClientRepositoryImpl;

public class ClientServiceImpl implements ClientService {

	private ClientRepository clientRepository = new ClientRepositoryImpl();

	@Override
	public Client save(Client client) throws ClientException {
		if (client.getName() == null) {
			throw new ClientException("Client name is mandatory");
		}
		if (client.getName().length() <= 2) {
			throw new ClientException("Client name must have at least 3 letters");
		}

		if (client.getNif() == null) {
			throw new ClientException("Client nif is mandatory");
		}
		if (client.getDateOfBirth() == null) {
			throw new ClientException("Client date of birth is mandatory");
		}

		if (client.getEmail() == null) {
			throw new ClientException("Client email is mandatory");
		}
		if (client.getPhone() == null) {
			throw new ClientException("Client phone number is mandatory");
		}
		if (client.getPassword() == null) {
			throw new ClientException("Client password is mandatory");
		}
		return clientRepository.save(client).get();

	}

	@Override
	public List<Client> getAll() {
		return clientRepository.findAll();

	}

	@Override
	public Client getById(Integer id) {

		return clientRepository.findById(id).get();

	}

	@Override
	public Client getByNib(String nif) {

		return clientRepository.findByNib(nif).get();

	}

	@Override
	public void deleteById(Integer id) {

		clientRepository.deleteById(id);
	}

}
