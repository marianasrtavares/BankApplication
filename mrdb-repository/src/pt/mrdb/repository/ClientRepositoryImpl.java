package pt.mrdb.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import pt.mrdb.database.DataBase;
import pt.mrdb.database.Operation;
import pt.mrdb.model.Account;
import pt.mrdb.model.Client;

public class ClientRepositoryImpl implements ClientRepository {

	@Override
	public Optional<Client> save(Client client) {
		if (client.getId() == null) {
			String query = "INSERT INTO clients (id, nif, password, name, date_of_birth, phone, mobile, email, occupation)"
					+ " VAlUES (" + client.getId() + ", '" + client.getNif() + "','" + client.getPassword() + "','"
					+ client.getName() + "','" + client.getDateOfBirth() + "','" + client.getPhone() + "','"
					+ client.getMobile() + "','" + client.getEmail() + "','" + client.getOccupation() + "');";
			DataBase.executeQuery(query, Operation.INSERT);
			ResultSet rs = DataBase.executeQuery("SELECT * FROM clients WHERE id= (SELECT MAX(id) FROM clients)",
					Operation.SELECT);
			return extractObject(rs);

		} else {
			String query = "UPDATE clients SET " + "nif = " + client.getNif() + ", password = '" + client.getPassword()
					+ "', name ='" + client.getName() + "', date_of_birth ='" + client.getDateOfBirth() + "', phone ='"
					+ client.getPhone() + "', mobile ='" + client.getMobile() + "', email = '" + client.getEmail()
					+ "', occupation = '" + client.getOccupation() + "'" + " WHERE id= " + client.getId() + ";";
			DataBase.executeQuery(query, Operation.UPDATE);
			ResultSet rs = DataBase.executeQuery("SELECT * FROM clients WHERE id= (SELECT MAX(id) FROM clients)",
					Operation.SELECT);

			return extractObject(rs);
		}
	}

	@Override
	public List<Client> findAll() {

		String query = "SELECT * FROM clients";
		ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
		return extractList(rs);

	}
	

	@Override
	public Optional<Client> findById(Integer id) {

		String query = "SELECT * FROM clients WHERE id =" + id;
		ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
		return extractObject(rs);
	}

	@Override
	public Optional<Client> findByNib(String nif) {
		String query = "SELECT * FROM clients WHERE nif LIKE" + nif;
		ResultSet rs = DataBase.executeQuery(query, Operation.SELECT);
		return extractObject(rs);
	}

	@Override
	public void deleteById(Integer id) {
		String query = "DELETE FROM clients WHERE id = " + id;
		DataBase.executeQuery(query, Operation.DELETE);

	}


	private List<Client> extractList(ResultSet rs) {
		List<Client> clients = new ArrayList<>();
		try {
			while (rs.next()) {
				Client client = buildObject(rs);
				clients.add(client);
			}
			return clients;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	private Optional<Client> extractObject(ResultSet rs) {
		try {
			if (rs.next()) {
				Client client = buildObject(rs);

				return Optional.of(client);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	private Client buildObject(ResultSet rs) throws SQLException {
		Client client = new Client();
		client.setId(rs.getInt(1));
		client.setNif(rs.getString(2));
		client.setPassword(rs.getString(3));
		client.setName(rs.getString(4));
		client.setDateOfBirth(rs.getDate(5).toLocalDate());
		client.setPhone(rs.getString(6));
		client.setMobile(rs.getString(7));
		client.setEmail(rs.getString(8));
		client.setOccupation(rs.getString(9));
		return client;
	}

}
