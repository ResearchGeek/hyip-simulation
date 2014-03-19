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
	
	private static ArrayList<Hyip> chooseAllProducers(Object contextBeing) {
		say("Choosing all HYIPs to participate in evolving");
		return GameController.chooseAllProducers(contextBeing);
	}
	
	public static void evolve(Object contextBeing){
		say("Executing stochasting universal sampling");
		Player.stochasticSampling(chooseAllProducers(contextBeing));
	}
	
	private void logActivity(String s) {
		PjiitOutputter.log(s);
	}
	
	private static void say(String s) {
		PjiitOutputter.say(s);
	}

}
