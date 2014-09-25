package HyipGame;

public class ExitStrategyOptions {

	private Boolean considerBalance;
	private Boolean considerInvestorCount;
	private Boolean considerIncome;
	private Boolean considerTime;

	public ExitStrategyOptions(Boolean... switches) {
		this.considerBalance = switches[0];
		this.considerInvestorCount = switches[1];
		this.considerIncome = switches[2];
		this.considerTime = switches[3];
	}

	public ExitStrategyOptions(Boolean switchTrue) {
		this.considerBalance = switchTrue;
		this.considerInvestorCount = switchTrue;
		this.considerIncome = switchTrue;
		this.considerTime = switchTrue;
	}

	public ExitStrategyOptions clone() {
		return new ExitStrategyOptions(this.considerBalance,
				this.considerInvestorCount, this.considerIncome,
				this.considerTime);
	}

	public Boolean isConsiderBalance() {
		return considerBalance;
	}

	public void setConsiderBalance(Boolean considerBalance) {
		this.considerBalance = considerBalance;
	}

	public Boolean isConsiderIncome() {
		return considerIncome;
	}

	public void setConsiderIncome(Boolean considerIncome) {
		this.considerIncome = considerIncome;
	}

	public Boolean isConsiderInvestorCount() {
		return considerInvestorCount;
	}

	public void setConsiderInvestorCount(Boolean considerInvestorCount) {
		this.considerInvestorCount = considerInvestorCount;
	}

	public Boolean isConsiderTime() {
		return considerTime;
	}

	public void setConsiderTime(Boolean considerTime) {
		this.considerTime = considerTime;
	}

}
