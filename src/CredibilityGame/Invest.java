package CredibilityGame;

import java.util.Date;

public class Invest {
	
	private static volatile long COUNT_INVESTS = 0;
	private Long InvestId;
	
	private double money; // amount of money "invested"
	private int tickCount; // 1 tick = 1 day in our simulation universe
	
	private Hyip hyip;
	private HyipOffert hyipOffert;
	private Investor investor;
	
	public Invest(Investor investor, Hyip hyip, double money, HyipOffert hyipOffert) {
		++COUNT_INVESTS;
		this.hyip = hyip;
		this.money = money;
		this.hyipOffert = hyipOffert;
		this.investor = investor;
		this.tickCount = 0;
		InvestId = COUNT_INVESTS;
	}

	public Investor getInvestor() {
		return investor;
	}

	public HyipOffert getHyipOffert() {
		return hyipOffert;
	}

	public Hyip getHyip() {
		return hyip;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getTickCount() {
		return tickCount;
	}

	public void setTickCount(int tickCount) {
		this.tickCount = tickCount;
	}
	
	public void incrementTickCount(){
		this.tickCount++;
	}
	
	public void calculateInterest(){
		this.money += this.money * hyipOffert.getPercent();
	}
	
	@Override
	public int hashCode() {
		return InvestId.hashCode() * investor.hashCode() * hyip.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if ( ((Invest)obj).InvestId == this.InvestId ){
			return true;
		} else
			return false;
	}

}
