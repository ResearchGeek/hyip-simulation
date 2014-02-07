package CredibilityGame;

import java.util.Date;

public class Invest {
	
	private double money;
	private double tickCount;
	
	private Hyip hyip;
	private HyipOffert hyipOffert;
	private Investor investor;
	
	public Invest(Investor investor, Hyip hyip, double money, HyipOffert hyipOffert) {
		this.hyip = hyip;
		this.money = money;
		this.hyipOffert = hyipOffert;
		this.investor = investor;
		this.tickCount = 0;
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

	public double getTickCount() {
		return tickCount;
	}

	public void setTickCount(double tickCount) {
		this.tickCount = tickCount;
	}

}
