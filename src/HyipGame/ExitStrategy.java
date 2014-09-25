package HyipGame;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import CredibilityGame.Strategy;

public class ExitStrategy implements Strategy {

	private ExitStrategyOptions exitStrategyOptions;

	private double balance;
	private int investorCount;
	private double income;
	private int time;

	private static Parameters params = RunEnvironment.getInstance()
			.getParameters();
	private static double bias = 0.01 * ((int) params
			.getInteger("first_strategy_bias"));

	public ExitStrategy(double balance, int investorCount, double income,
			int time, Boolean... enabled) {
		this.balance = balance;
		this.investorCount = investorCount;
		this.income = income;
		this.time = time;
		this.exitStrategyOptions = new ExitStrategyOptions();
		for (boolean enable : enabled) {
			if (isN(exitStrategyOptions.isConsiderBalance())) {
				exitStrategyOptions.setConsiderBalance(enable);
				continue;
			}
			if (isN(exitStrategyOptions.isConsiderInvestorCount())) {
				exitStrategyOptions.setConsiderInvestorCount(enable);
				continue;
			}
			if (isN(exitStrategyOptions.isConsiderIncome())) {
				exitStrategyOptions.setConsiderIncome(enable);
				continue;
			}
			if (isN(exitStrategyOptions.isConsiderTime())) {
				exitStrategyOptions.setConsiderTime(enable);
				continue;
			}
		}
	}

	public ExitStrategy(double income, int investorCount, double balance,
			int time) {
		this.income = income;
		this.investorCount = investorCount;
		this.balance = balance;
		this.time = time;
		this.exitStrategyOptions = new ExitStrategyOptions();
	}

	public ExitStrategy(double income, int investorCount, double balance,
			int time, ExitStrategyOptions exitStrategyOptions) {
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

	@Override
	public ExitStrategy copy() {
		return new ExitStrategy(getIncome(), getInvestorCount(), getBalance(),
				getTime(), getExitStrategyOptions().clone());
	}

	@Override
	public void clear() {
		ExitStrategy typical = ExitStrategyUtilities.getTypicalStrategy();
		this.exitStrategyOptions = typical.getExitStrategyOptions();
		this.balance = typical.balance;
		this.income = typical.income;
		this.time = typical.time;
		this.investorCount = typical.investorCount;

	}

	public void copyStrategy(ExitStrategy copyFrom) {
		this.exitStrategyOptions = copyFrom.getExitStrategyOptions().clone();
		this.balance = copyFrom.balance;
		this.income = copyFrom.income;
		this.time = copyFrom.time;
		this.investorCount = copyFrom.investorCount;
	}

	@Override
	public void copyStrategy(Strategy copyFrom) {
		// this will never happen whatsoever - artifact from CredibilityGame
		// proper method we have above
		// throw new UnsupportedOperationException(
		// "We don't use producer/consumer game in HYIP simulation");
		System.exit(-10);
	}

	/**
	 * Update agents strategy within the statistic found to be good for first
	 * generation. Variable "first" states if is is a first generation, just to
	 * be sure that developer understands how to use this method ;)
	 * 
	 * @param hyipStatistics
	 * @param first
	 */
	public void updateFromStats(HyipStatistics hyipStatistics, boolean first) {
		if (!first)
			// throw new javax.help.UnsupportedOperationException();
			System.exit(-10);

		double variance = 0;

		this.balance = hyipStatistics.getCash();
		variance = this.balance * bias;
		this.balance += (RandomHelper.nextDoubleFromTo(-variance, variance));
		this.exitStrategyOptions.setConsiderBalance(RandomHelper.nextIntFromTo(
				0, 1) == 0 ? false : true);

		this.income = hyipStatistics.getIncome();
		variance = this.income * bias;
		this.income += (RandomHelper.nextDoubleFromTo(-variance, variance));
		this.exitStrategyOptions.setConsiderIncome(RandomHelper.nextIntFromTo(
				0, 1) == 0 ? false : true);

		this.time = hyipStatistics.getTick();
		variance = this.time * bias;
		this.time += (int) (RandomHelper.nextDoubleFromTo(-variance, variance));
		this.exitStrategyOptions.setConsiderTime(RandomHelper.nextIntFromTo(0,
				1) == 0 ? false : true);

		this.investorCount = hyipStatistics.getInvestorCount();
		variance = this.investorCount * bias;
		this.investorCount += (int) (RandomHelper.nextDoubleFromTo(-variance,
				variance));
		this.exitStrategyOptions.setConsiderInvestorCount(RandomHelper
				.nextIntFromTo(0, 1) == 0 ? false : true);
	}
}
