package pt.mrdb.model;

public class CreditCard extends Card {
	
	private Double plafond;
	

	public CreditCard(Client client, Account account, Double plafond) {
		super(client, account);
		this.plafond = plafond;
	 } 

	public CreditCard() {
		// TODO Auto-generated constructor stub
	}

	public Double getPlafond() {
		return plafond;
	}

	public void setPlafond(Double plafond) {
		this.plafond = plafond;
	}

	@Override
	public String toString() {
		return "CreditCard "+ super.toString() +"plafond=" + String.format("%.2f",plafond)+"\n";
	}
	
	
}
