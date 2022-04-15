package pt.mrdb.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Account {

	private List<Double> transationHistory = new ArrayList<>();
	private Integer id;
	private Integer nib;
	private Client primaryClient;
	private Set<Client> secondaryClient = new HashSet<>();
	private Double balance;

	public Account(Integer nib, Client primaryClient, Double balance) {
		this.nib = nib;
		this.primaryClient = primaryClient;
		this.balance = balance;
		transationHistory.add(balance);
	}

	public Account() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNib() {
		return nib;
	}

	public void setNib(Integer nib) {
		this.nib = nib;
	}

	public Client getPrimaryClient() {
		return primaryClient;
	}

	public void setPrimaryClient(Client primaryClient) {
		this.primaryClient = primaryClient;
	}

	public Set<Client> getSecondaryClient() {
		return secondaryClient;
	}

	public void addSecondaryClient(Client client) {
		secondaryClient.add(client);
	}

	public void setSecondaryClient(Set<Client> secondaryClient) {
		this.secondaryClient = secondaryClient;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
		transationHistory.add(this.balance);

	}

	public List<Double> getTransationHistory() {
		return transationHistory;
	}

	public void setTransationHistory(List<Double> transationHistory) {
		this.transationHistory = transationHistory;
	}

	@Override
	public String toString() {
		return "Account id=" + id + ", primaryClient=" + primaryClient + ", balance=" + String.format("%.2f", balance)
				+ "\n";
	}

}
