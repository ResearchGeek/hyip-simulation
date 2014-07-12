package HyipGame;

import java.util.ArrayList;

import logger.PjiitOutputter;
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

	public static void evolve(Object contextBeing) {
		say("Executing stochasting universal sampling");
		ArrayList<Hyip> g = chooseGoodHyips(contextBeing);
		ArrayList<Hyip> b = chooseBadHyips(contextBeing);
		say("There are " + g.size() + " good hyips and " + b.size()
				+ " bad hyips");
		say("E_use of 1st good hyip before evolution: " + g.get(0).getE_use());
		say("P_use of 1st good hyip before evolution: " + g.get(0).getP_use());
		Player.stochasticSampling(g);
		Player.stochasticSampling(b);
		say("E_use of 1st good hyip after evolution: " + g.get(0).getE_use());
		say("P_use of 1st good hyip after evolution: " + g.get(0).getP_use());
	}

	private void logActivity(String s) {
		PjiitOutputter.log(s);
	}

	private static void say(String s) {
		PjiitOutputter.say(s);
	}

}
