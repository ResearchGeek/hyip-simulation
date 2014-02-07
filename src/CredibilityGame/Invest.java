package CredibilityGame;

import java.util.Date;

public class Invest {
	
	private double money;
	private Date time;
	private double howLong;
	
	private Hyip hyip;
	private HyipOffert hyipOffert;
	private Investor investor;
	
	public Invest(Investor investor, Hyip hyip, int invest, HyipOffert hyipOffert) {
		this.hyip = hyip;
		this.money = invest;
		this.hyipOffert = hyipOffert;
		this.investor = investor;
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

}
