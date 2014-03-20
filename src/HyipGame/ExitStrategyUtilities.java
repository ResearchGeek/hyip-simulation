package HyipGame;

import CredibilityGame.Hyip;

public class ExitStrategyUtilities {

	private static final double TYPICAL_INCOME = 1000;
	private static final int TYPICAL_INVESTORCOUNT = 1000;
	private static final double TYPICAL_BALANCE = 1000;
	private static final int TYPICAL_TIME = 100;

	public static void assignInitialStrategy(Hyip hyip) {
		hyip.setExitStrategy(getTypicalStrategy());
	}

	public static ExitStrategy getTypicalStrategy() {
		ExitStrategy exitStrategy = new ExitStrategy(TYPICAL_INCOME,
				TYPICAL_INVESTORCOUNT, TYPICAL_BALANCE, TYPICAL_TIME, true,
				true, true, true);
		return exitStrategy;
	}

	public static Boolean checkForPass(Hyip hyip) {
		boolean fail = false;
		//boolean considerationFail = false;
		boolean considerationMade = false;

		ExitStrategy exitStrategy = hyip.getExitStrategy();
		ExitStrategyOptions exitStrategyOptions = hyip.getExitStrategy()
				.getExitStrategyOptions();
		if (exitStrategyOptions.isConsiderIncome()) {
			considerationMade = true;
			if (hyip.getIncome() < exitStrategy.getIncome()) {
				fail = true;
			}
		}
		if (exitStrategyOptions.isConsiderBalance()) {
			considerationMade = true;
			if (hyip.getCash() < exitStrategy.getBalance()) {
				fail = true;
			}
		}
		if (exitStrategyOptions.isConsiderInvestorCount()) {
			considerationMade = true;
			if (hyip.countOngoingInvestments() < exitStrategy
					.getInvestorCount()) {
				fail = true;
			}
		}
		if (exitStrategyOptions.isConsiderTime()) {
			considerationMade = true;
			if (hyip.getGameController().getCurrentIteration() < exitStrategy
					.getTime()) {
				fail = true;
			}
		}
		// answer the ultimate question if to close a HYIP here
		return ((considerationMade) && (!fail));
	}

}
