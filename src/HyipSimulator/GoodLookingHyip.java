package HyipSimulator;

import HyipGame.ExitStrategyUtilities;
import Players.Hyip;
import Players.HyipType.GoodLooking;

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
