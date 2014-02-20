package HyipGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import repast.simphony.context.Context;
import repast.simphony.util.ContextUtils;
import CredibilityGame.Hyip;
import CredibilityGame.Player;

public class HyipEvolve {
	
	@SuppressWarnings("unchecked")
	private static ArrayList<Hyip> chooseAllProducers(Object contextBeing) {
		Context<Player> context = ContextUtils.getContext(contextBeing);
		Iterable<Player> it = context.getObjects(Hyip.class);
		ArrayList<Hyip> result = new ArrayList<Hyip>();
		Iterator<Player> iterator = it.iterator();
		while (iterator.hasNext()) {
			result.add((Hyip) iterator.next());
		}
		return result;
	}
	
	public static void evolve(Object contextBeing){
		Player.stochasticSampling(chooseAllProducers(contextBeing));
	}

}
