package CredibilityGame;

import CredibilityGame.HyipType.BadLooking;
import HyipGame.ExitStrategyUtilities;

public class BadLookingHyip extends Hyip {
	
	public BadLookingHyip(BadLooking badLooking){
		super(badLooking);
		initStrategy();
	}
	
	private void initStrategy(){
		ExitStrategyUtilities.assignInitialStrategy(this);
	}

}
