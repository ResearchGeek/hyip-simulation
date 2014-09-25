package HyipGame;

import CredibilityGame.Hyip;

public class ExitStrategyUtilities {

	private static final double TYPICAL_BALANCE = 1000;
	private static final int TYPICAL_INVESTORCOUNT = 1000;
	private static final double TYPICAL_INCOME = 1000;
	private static final int TYPICAL_TIME = 100;

	public static void assignInitialStrategy(Hyip hyip) {
		hyip.setExitStrategy(getTypicalStrategy());
	}

	public static ExitStrategy getTypicalStrategy() {
		ExitStrategy exitStrategy = new ExitStrategy(TYPICAL_BALANCE,
				TYPICAL_INVESTORCOUNT, TYPICAL_INCOME, TYPICAL_TIME, false,
				false, false, false);
		return exitStrategy;
	}

	public static Boolean checkForPass(Hyip hyip) {
		boolean hyipQuits = true;
		boolean considerationMade = false;

		ExitStrategy exitStrategy = hyip.getExitStrategy();
		ExitStrategyOptions exitStrategyOptions = hyip.getExitStrategy()
				.getExitStrategyOptions();
		if (exitStrategyOptions.isConsiderIncome()) {
			considerationMade = true;
			if (hyip.getIncome() > exitStrategy.getIncome()) {
				hyipQuits = false;
			}
		}
		if (exitStrategyOptions.isConsiderBalance()) {
			considerationMade = true;
			if (hyip.getCash() < exitStrategy.getBalance()) {
				hyipQuits = false;
			}
		}
		if (exitStrategyOptions.isConsiderInvestorCount()) {
			considerationMade = true;
			if (hyip.countOngoingInvestments() < exitStrategy
					.getInvestorCount()) {
				hyipQuits = false;
			}
		}
		if (exitStrategyOptions.isConsiderTime()) {
			considerationMade = true;
			if (hyip.getGameController().getCurrentIteration() < exitStrategy
					.getTime()) {
				hyipQuits = false;
			}
		}
		// answer the ultimate question if to close a HYIP here
		return ((considerationMade) && (hyipQuits));
	}

}
