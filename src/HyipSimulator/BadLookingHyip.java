package HyipSimulator;

import HyipGame.ExitStrategyUtilities;
import Players.Hyip;
import Players.HyipType.BadLooking;

public class BadLookingHyip extends Hyip {
	
	public BadLookingHyip(BadLooking badLooking){
		super(badLooking);
		initStrategy();
	}
	
	/**
	 * Assigning to the Hyip the initial exit strategy
	 */
	private void initStrategy(){
		ExitStrategyUtilities.assignInitialStrategy(this);
	}

}
