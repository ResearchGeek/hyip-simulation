package HyipGame;

public class ExitStrategyOptions {

	private Boolean considerIncome;
	private Boolean considerInvestorCount;
	private Boolean considerBalance;
	private Boolean considerTime;
	
	//private Boolean considerE_use;
	//private Boolean considerP_use;

	public ExitStrategyOptions() {
		// consider done mister.
	}

	public ExitStrategyOptions(Boolean switchTrue) {
		this.considerBalance = switchTrue;
		this.considerInvestorCount = switchTrue;
		this.considerBalance = switchTrue;
		this.considerTime = switchTrue;
		//this.considerE_use = switchTrue;
		//this.considerP_use = switchTrue;
	}

	public ExitStrategyOptions(boolean switchTrue) {
		this.considerBalance = switchTrue;
		this.considerInvestorCount = switchTrue;
		this.considerBalance = switchTrue;
		this.considerTime = switchTrue;
		//this.considerE_use = switchTrue;
		//this.considerP_use = switchTrue;
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

//	public Boolean isConsiderE_use() {
//		return considerE_use;
//	}
//
//	public void setConsiderE_use(Boolean considerE_use) {
//		this.considerE_use = considerE_use;
//	}
//
//	public Boolean isConsiderP_use() {
//		return considerP_use;
//	}
//
//	public void setConsiderP_use(Boolean considerP_use) {
//		this.considerP_use = considerP_use;
//	}

}
