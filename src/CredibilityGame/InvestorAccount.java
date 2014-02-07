package CredibilityGame;

public class InvestorAccount {

	private Investor owner;
	private double balance;

	public InvestorAccount(Investor owner) {
		this.owner = owner;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Boolean spendMoney(double investMoney) {
		if (balance - investMoney < 0) {
			return false; // invest failed, no money in user funds !
		} else {
			balance -= investMoney;
			return true;
		}
	}

	public Investor getOwner() {
		return owner;
	}

	public void setOwner(Investor owner) {
		this.owner = owner;
	}

}
