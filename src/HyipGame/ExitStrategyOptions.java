package HyipGame;

public class ExitStrategyOptions {

	private Boolean considerIncome;
	private Boolean considerInvestorCount;
	private Boolean considerBalance;
	private Boolean considerTime;

	public ExitStrategyOptions() {
		// consider done mister.
	}

	public ExitStrategyOptions(Boolean switchTrue) {
		this.considerBalance = switchTrue;
		this.considerInvestorCount = switchTrue;
		this.considerBalance = switchTrue;
		this.considerTime = switchTrue;
	}

	public ExitStrategyOptions(boolean switchTrue) {
		this.considerBalance = switchTrue;
		this.considerInvestorCount = switchTrue;
		this.considerBalance = switchTrue;
		this.considerTime = switchTrue;
	}

	public Boolean isConsiderBalance() {
		return considerBalance;
	}

	public void setConsiderBalance(boolean considerBalance) {
		this.considerBalance = considerBalance;
	}

	public Boolean isConsiderIncome() {
		return considerIncome;
	}

	public void setConsiderIncome(boolean considerIncome) {
		this.considerIncome = considerIncome;
	}

	public Boolean isConsiderInvestorCount() {
		return considerInvestorCount;
	}

	public void setConsiderInvestorCount(boolean considerInvestorCount) {
		this.considerInvestorCount = considerInvestorCount;
	}

	public Boolean isConsiderTime() {
		return considerTime;
	}

	public void setConsiderTime(boolean considerTime) {
		this.considerTime = considerTime;
	}

}
