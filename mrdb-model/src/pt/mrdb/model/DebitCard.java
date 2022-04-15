package pt.mrdb.model;

public class DebitCard extends Card {

	private Double dailyWithdrawal;


	public DebitCard(Client client, Account account, Double dailyWithdrawal) {
		super(client, account);
		this.dailyWithdrawal = dailyWithdrawal;
	}

	public DebitCard() {
		// TODO Auto-generated constructor stub
	}

	public Double getDailyWithdrawal() {
		return dailyWithdrawal;
	}

	public void setDailyWithdrawal(Double dailyWithdrawal) {
		this.dailyWithdrawal = dailyWithdrawal;
	}

	@Override
	public String toString() {
		return  "DebitCard "+super.toString()+ "dailyWithdrawal=" + String.format("%.2f",dailyWithdrawal)+"\n";
	}

}
