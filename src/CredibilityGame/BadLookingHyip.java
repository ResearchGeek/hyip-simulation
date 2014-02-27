package CredibilityGame;

import CredibilityGame.HyipType.BadLooking;
import HyipGame.ExitStrategyUtilities;

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
