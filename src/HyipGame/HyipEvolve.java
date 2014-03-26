package HyipGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import logger.PjiitOutputter;
import repast.simphony.context.Context;
import repast.simphony.util.ContextUtils;
import CredibilityGame.GameController;
import CredibilityGame.Hyip;
import CredibilityGame.Player;

public class HyipEvolve {
	
	private static ArrayList<Hyip> chooseGoodHyips(Object contextBeing) {
		say("Choosing all good HYIPs to participate in evolving");
		return GameController.chooseSpecificProducers(contextBeing, true);
	}
	
	private static ArrayList<Hyip> chooseBadHyips(Object contextBeing) {
		say("Choosing all bad HYIPs to participate in evolving");
		return GameController.chooseSpecificProducers(contextBeing, false);
	}
	
	public static void evolve(Object contextBeing){
		say("Executing stochasting universal sampling");
		Player.stochasticSampling(chooseGoodHyips(contextBeing));
		Player.stochasticSampling(chooseBadHyips(contextBeing));
	}
	
	private void logActivity(String s) {
		PjiitOutputter.log(s);
	}
	
	private static void say(String s) {
		PjiitOutputter.say(s);
	}

}
