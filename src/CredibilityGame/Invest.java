package CredibilityGame;

import java.util.Date;

public class Invest {
	
	private double money;
	private Date time;
	private double howLong;
	
	private Hyip hyip;
	private HyipOffert hyipOffert;
	
	public Invest(Hyip hyip, int invest, InvestorType investorType) {
		this.hyip = hyip;
		this.money = invest;
		this.hyipOffert = hyip.getOffert(investorType);
	}
	

}
