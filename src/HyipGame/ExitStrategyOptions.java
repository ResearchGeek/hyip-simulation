package HyipGame;

public class ExitStrategyOptions {
	
	private boolean considerIncome;
	private boolean considerInvestorCount;
	private boolean considerBalance;
	public boolean isConsiderBalance() {
		return considerBalance;
	}
	public void setConsiderBalance(boolean considerBalance) {
		this.considerBalance = considerBalance;
	}
	public boolean isConsiderIncome() {
		return considerIncome;
	}
	public void setConsiderIncome(boolean considerIncome) {
		this.considerIncome = considerIncome;
	}
	public boolean isConsiderInvestorCount() {
		return considerInvestorCount;
	}
	public void setConsiderInvestorCount(boolean considerInvestorCount) {
		this.considerInvestorCount = considerInvestorCount;
	}

}
