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
		System.out.println("Players context loaded");
		Parameters params = RunEnvironment.getInstance().getParameters();
		int producerPopulationSize = (Integer) params
				.getValue("hyip_population_size"); // 8

		int inv_0 = (Integer) params.getValue("inv_0");
		int inv_1 = (Integer) params.getValue("inv_1");
		int inv_2 = (Integer) params.getValue("inv_2");

		Hyip.initialize();

		Iterator<GoodLooking> goodLookingHyips = HyipType.goodLooking
				.iterator();
		for (int i = 0; i < (producerPopulationSize / 2); i++) {
			this.add(new GoodLookingHyip(goodLookingHyips.next()));
		}

		Iterator<BadLooking> badLookingHyips = HyipType.badLooking.iterator();
		for (int i = producerPopulationSize / 2; i < (producerPopulationSize); i++) {
			this.add(new BadLookingHyip(badLookingHyips.next()));
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
