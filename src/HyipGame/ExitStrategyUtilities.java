package HyipGame;

import CredibilityGame.Hyip;

public class ExitStrategyUtilities {

	private static final double TYPICAL_INCOME = 1000;
	private static final int TYPICAL_INVESTORCOUNT = 1000;
	private static final double TYPICAL_BALANCE = 1000;
	private static final int TYPICAL_TIME = 100;

	public static void assignInitialStrategy(Hyip hyip) {
		ExitStrategy exitStrategy = new ExitStrategy(TYPICAL_INCOME,
				TYPICAL_INVESTORCOUNT, TYPICAL_BALANCE, TYPICAL_TIME, true,
				true, true, true);
		hyip.setExitStrategy(exitStrategy);
	}

	public static Boolean checkForPass(Hyip hyip) {
		boolean result = false;
		boolean considerationFail = false;
		boolean considerationMade = false;
		
		ExitStrategy exitStrategy = hyip.getExitStrategy();
		ExitStrategyOptions exitStrategyOptions = 
				hyip.getExitStrategy().getExitStrategyOptions();
		if (exitStrategyOptions.isConsiderIncome()) {
			considerationMade = true;
			if (hyip.getIncome() >= exitStrategy.getIncome()){
				result = true;
			} else {
				considerationFail = true;
			}
		}
		if (exitStrategyOptions.isConsiderBalance()) {
			considerationMade = true;
			if (hyip.getCash() >= exitStrategy.getBalance()){
				result = true;
			} else {
				considerationFail = true;
			}
		}
		if (exitStrategyOptions.isConsiderInvestorCount()) {
			considerationMade = true;
			if (hyip.getTotalNumberOfInvestments() >= exitStrategy.getInvestorCount()){
				result = true;
			} else {
				considerationFail = true;
			}
		}
		if (exitStrategyOptions.isConsiderTime()) {
			considerationMade = true;
			if (hyip.getCurrentIteration() >= exitStrategy.getTime()){
				result = true;
			} else {
				considerationFail = true;
			}
		}
		//answer the ultimate question if to close a HYIP here
		return ((considerationMade)&&(result)&&(!considerationFail));
	}

}
