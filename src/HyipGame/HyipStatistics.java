package HyipGame;

public class HyipStatistics {
	
	// holds the peek moment
	private int tick;
	private int investorCount;
	private double income;
	private double cash;
	
	public HyipStatistics(){
		this.cash = 0;
	}
	
	public void clear(){
		this.tick = 0;
		this.investorCount = 0;
		this.income = 0;
		this.cash = 0;
	}
	
	public int getTick() {
		return tick;
	}
	public void setTick(int tick) {
		this.tick = tick;
	}
	public int getInvestorCount() {
		return investorCount;
	}
	public void setInvestorCount(int investorCount) {
		this.investorCount = investorCount;
	}
	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	public double getCash() {
		return cash;
	}
	public void setCash(double cash) {
		this.cash = cash;
	}
	public boolean isBetterMoment(double cash){
		return cash > this.cash;
	}

}
