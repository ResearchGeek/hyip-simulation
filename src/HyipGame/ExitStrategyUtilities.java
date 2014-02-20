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

}
