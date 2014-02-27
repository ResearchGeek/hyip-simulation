package CredibilityGame;

import CredibilityGame.HyipType.GoodLooking;
import HyipGame.ExitStrategyUtilities;

public class GoodLookingHyip extends Hyip {
	
	public GoodLookingHyip(GoodLooking goodLooking){
		super(goodLooking);
		initStrategy();
	}
	
	/**
	 * Assigning to the Hyip the initial exit strategy
	 */
	private void initStrategy(){
		ExitStrategyUtilities.assignInitialStrategy(this);
	}

}
