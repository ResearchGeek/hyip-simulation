package CredibilityGame;

import CredibilityGame.HyipType.GoodLooking;
import HyipGame.ExitStrategyUtilities;

public class GoodLookingHyip extends Hyip {
	
	public GoodLookingHyip(GoodLooking goodLooking){
		super(goodLooking);
		initStrategy();
	}
	
	private void initStrategy(){
		ExitStrategyUtilities.assignInitialStrategy(this);
	}

}
