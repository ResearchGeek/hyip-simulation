package CredibilityGame;

public class InvestorAccount {
	
	private Investor owner;
	private double invest_money;
	
	public InvestorAccount(Investor owner){
		this.owner = owner;
	}

	public double getInvest_money() {
		return invest_money;
	}

	public void setInvest_money(double invest_money) {
		this.invest_money = invest_money;
	}

	public Investor getOwner() {
		return owner;
	}

	public void setOwner(Investor owner) {
		this.owner = owner;
	}
	
}
