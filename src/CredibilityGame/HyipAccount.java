package CredibilityGame;

public class HyipAccount {
	
	private Hyip owner;
	private double cash;
	
	public HyipAccount(Hyip owner, double cash){
		this.owner = owner;
		this.cash = cash;
	}
	
	public HyipAccount(Hyip owner){
		throw new UnsupportedOperationException("Don't use this constructor");
	}
	
	public void addMoney(double value) {
		cash += value;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public Hyip getOwner() {
		return owner;
	}

	public void setOwner(Hyip owner) {
		throw new UnsupportedOperationException("I doubt account can change owner");
		//this.owner = owner;
	}

}
