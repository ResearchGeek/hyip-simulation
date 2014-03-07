package CredibilityGame;

import repast.simphony.engine.environment.RunEnvironment;

public class InvestorAccount {

	private Investor owner;
	private double balance;
	
	private static boolean unlimited = 
			RunEnvironment.getInstance().getParameters().getBoolean("unlimited_i_funds");

	public InvestorAccount(Investor owner) {
		this.owner = owner;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public Boolean hasMoney(){
		return ( unlimited ? true : (balance > 0) );
	}
	
	public Boolean hasMoney(double howMuch){
		return ( unlimited ? true : (balance >= howMuch) );
	}

	public Boolean spendMoney(double investMoney) {
		if ( (unlimited) || (balance - investMoney >= 0)){
			balance -= investMoney;
			return true;
		} else {
			return false; // invest failed, no money in user funds !
		}
	}
	
	public void addFunds(double money){
		this.balance += money;
	}

	public Investor getOwner() {
		return owner;
	}

	public void setOwner(Investor owner) {
		this.owner = owner;
	}
	
	public void clear(){
		this.balance = 0;
	}

}
