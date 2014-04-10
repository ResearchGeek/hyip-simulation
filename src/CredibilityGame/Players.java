package CredibilityGame;

import java.util.Iterator;

import repast.simphony.context.DefaultContext;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import CredibilityGame.HyipType.BadLooking;
import CredibilityGame.HyipType.GoodLooking;

public class Players extends DefaultContext<Player> {

	public Players() {
		super("Players");
		System.out.println(Constraints.PLAYERS_LOADED);
		Parameters params = RunEnvironment.getInstance().getParameters();
		int producerPopulationSize = (Integer) params
				.getValue("hyip_population_size");

		int inv_0 = (Integer) params.getValue("inv_0");
		int inv_1 = (Integer) params.getValue("inv_1");
		int inv_2 = (Integer) params.getValue("inv_2");

		Hyip.initialize();

		Iterator<GoodLooking> goodLookingHyips = HyipType.goodLooking
				.iterator();
		for (int x = 0 ; x < HyipType.goodLooking.size() ; x++){
			assert goodLookingHyips.hasNext();
			GoodLooking goodLooking = goodLookingHyips.next();
			int z = (producerPopulationSize / 2) / HyipType.goodLooking.size();
			for (int i = 0; i < z; i++) {
				GoodLookingHyip goodLookingHyip = new GoodLookingHyip(goodLooking);
				this.add(goodLookingHyip);
			}
		}

		Iterator<BadLooking> badLookingHyips = HyipType.badLooking.iterator();
		for (int x = 0 ; x < HyipType.badLooking.size() ; x++){
			assert badLookingHyips.hasNext();
			BadLooking badLooking = badLookingHyips.next();
			int z = (producerPopulationSize / 2) / HyipType.badLooking.size();
			for (int i = 0; i < z; i++) {
				BadLookingHyip badLookingHyip = new BadLookingHyip(badLooking);
				this.add(badLookingHyip);
			}
		}

		for (int i = 0; i < inv_0; i++) {
			Investor investor = new Investor(InvestorType.HIGH_AVERSION);
			investor.initializeWallet();
			this.add(investor);
		}
		for (int i = 0; i < inv_1; i++) {
			Investor investor = new Investor(InvestorType.MEDIUM_AVERSION);
			investor.initializeWallet();
			this.add(investor);
		}
		for (int i = 0; i < inv_2; i++) {
			Investor investor = new Investor(InvestorType.LOW_AVERSION);
			investor.initializeWallet();
			this.add(investor);
		}

	}
}
