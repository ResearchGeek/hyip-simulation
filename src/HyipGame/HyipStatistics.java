package HyipGame;

public class HyipStatistics {
	
	// holds the peek moment
	private int tick;
	private int investorCount;
	private double income;
	private double cash;
	
	private double e_use;
	private double p_use;
	
	public HyipStatistics(){
		this.cash = 0;
	}
	
	public void clear(){
		this.tick = 0;
		this.investorCount = 0;
		this.income = 0;
		this.cash = 0;
		this.e_use = 0;
		this.p_use = 0;
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

	public double getE_use() {
		return e_use;
	}

	public void setE_use(double e_use) {
		this.e_use = e_use;
	}

	public double getP_use() {
		return p_use;
	}

	public void setP_use(double p_use) {
		this.p_use = p_use;
	}

}
