package HyipGame;

public class ExitStrategy {
	
	private ExitStrategyOptions exitStrategyOptions;
	
	private double income;
	private int investorCount;
	private double balance;
	
	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	public int getInvestorCount() {
		return investorCount;
	}
	public void setInvestorCount(int investorCount) {
		this.investorCount = investorCount;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public ExitStrategyOptions getExitStrategyOptions() {
		return exitStrategyOptions;
	}
	public void setExitStrategyOptions(ExitStrategyOptions exitStrategyOptions) {
		this.exitStrategyOptions = exitStrategyOptions;
	}

}
