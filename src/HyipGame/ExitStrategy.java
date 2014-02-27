package HyipGame;

public class ExitStrategy {

	private ExitStrategyOptions exitStrategyOptions;

	private double income;
	private int investorCount;
	private double balance;
	private int time;

	public ExitStrategy(double income, int investorCount, double balance, int time,
			Boolean... enabled) {
		this.income = income;
		this.investorCount = investorCount;
		this.balance = balance;
		this.time = time;
		this.exitStrategyOptions = new ExitStrategyOptions();
		for (boolean enable : enabled) {
			if (isN(exitStrategyOptions.isConsiderBalance())) {
				exitStrategyOptions.setConsiderBalance(enable);
				continue;
			}
			if (isN(exitStrategyOptions.isConsiderIncome())) {
				exitStrategyOptions.setConsiderIncome(enable);
				continue;
			}
			if (isN(exitStrategyOptions.isConsiderInvestorCount())) {
				exitStrategyOptions.setConsiderInvestorCount(enable);
				continue;
			}
			if (isN(exitStrategyOptions.isConsiderTime())) {
				exitStrategyOptions.setConsiderTime(enable);
				continue;
			}
		}
	}

	public ExitStrategy(double income, int investorCount, double balance, int time) {
		this.income = income;
		this.investorCount = investorCount;
		this.balance = balance;
		this.time = time;
		this.exitStrategyOptions = new ExitStrategyOptions();
	}

	public ExitStrategy(double income, int investorCount, double balance, int time,
			ExitStrategyOptions exitStrategyOptions) {
		this.income = income;
		this.investorCount = investorCount;
		this.balance = balance;
		this.time = time;
		this.exitStrategyOptions = exitStrategyOptions;
	}

	private boolean isN(Boolean option) {
		return option == null ? true : false;
	}

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

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

}