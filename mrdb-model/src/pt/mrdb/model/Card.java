package pt.mrdb.model;

import java.util.Random;

public class Card {

	private Integer id;
	private Client client;
	private Account account;
	private String pin;
	private Short isOriginalPin;

	public Card() {
		this.pin=defineAutomaticPin() ;
		this.isOriginalPin=1;

	}

	public Card(Client client, Account account) {
		this();
		this.client = client;
		this.account = account;
	}
	public String defineAutomaticPin() {
		Random random = new Random();
		String pin = String.format("%04d", 1000 + random.nextInt(9000) - 1);
		return pin;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}
	

	public Short getIsOriginalPin() {
		return isOriginalPin;
	}

	public void setIsOriginalPin(Short i) {
		this.isOriginalPin = i;
	}

	@Override
	public String toString() {
		return "card id=" + id + ", client id " + client.getId() + ", account id"
				+ account.getId() + "card pin=" + pin + " ";
	}

}
