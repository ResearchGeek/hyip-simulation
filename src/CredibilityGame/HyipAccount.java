package CredibilityGame;

public class HyipAccount {

	private Hyip owner;
	private double cash;
	private double income;

	public HyipAccount(Hyip owner, double cash) {
		this.owner = owner;
		this.cash = cash;
		this.income = 0;
	}

	public HyipAccount(Hyip owner) {
		throw new UnsupportedOperationException("Don't use this constructor");
	}

	public void addMoney(double value) {
		cash += value;
		addIncome(value);
	}

	public void withdrawMoney(double value) {
		cash -= value;
		addIncome(-value);
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}
	
	public void addIncome(double amount) {
		this.income += amount;
	}

	public Hyip getOwner() {
		return owner;
	}
	
	public void clear(){
		this.income = 0;
		this.cash = 0;
	}

	public void setOwner(Hyip owner) {
		throw new UnsupportedOperationException(
				"I doubt account can change owner");
		// this.owner = owner;
	}

}
