package CredibilityGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.jgap.impl.BestChromosomesSelector;

import CredibilityGame.producerchoice.strategy.ByReputationProducerStrategy;
import CredibilityGame.producerchoice.strategy.ProducerChoiceStrategy;
import CredibilityGame.producerchoice.strategy.RandomProducerStrategy;
import CredibilityGame.rating.strategy.PayoffDependentRatingStrategy;
import CredibilityGame.rating.strategy.RandomRatingStrategy;
import CredibilityGame.rating.strategy.SignalDependentRatingStrategy;
import CredibilityGame.rating.strategy.RatingStrategy;
import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.util.ContextUtils;
import CredibilityGame.producerchoice.strategy.ProducerChoiceStrategy;
import CredibilityGame.rating.strategy.RatingStrategy;
import repast.simphony.util.collections.IndexedIterable;

public class Investor extends Player {

	public static HashMap<String, Double> PAYOFFS = new HashMap<String, Double>();
	private static int CONSUMER_TYPE_H;
	private static int CONSUMER_TYPE_L;
	private int risk_level;
	private int invest_money; // do zmiany
	private double inv_invest;
	private double inv_rec;
	private int expertise;
	private RatingStrategy ratingStrategy;

	public static void initialize() {
		Parameters params = RunEnvironment.getInstance().getParameters();
	}

	public Investor(int type) {
		this.expertise = RandomHelper.createUniform(CONSUMER_TYPE_L,
				CONSUMER_TYPE_H).nextInt();
		Parameters params = RunEnvironment.getInstance().getParameters();
		inv_invest = (Double) params.getValue("inv_invest");
		inv_rec = (Double) params.getValue("inv_rec");
		risk_level = type;
	}

	@ScheduledMethod(start = 1.0, interval = 1.0, priority = 100)
	public void step() {
		Hyip hyipOwner = chooseProducer();
		invest_money = (int) (invest_money * (1 + hyipOwner.perc));
		if (Math.random() < inv_invest + hyipOwner.getAdvert())
			invest(hyipOwner);
		if (Math.random() < inv_rec)
			hyipOwner.makeWithdrawal(invest_money);
	}

	private void invest(Hyip hyip) {
		int invest = 0;
		if (risk_level == 0)
			invest = (int) (Math.random() * 4) + 1;
			new Invest(hyip, invest, risk_level);
		if (risk_level == 1)
			invest = (int) (Math.random() * 10) + 5;
			new Invest(hyip, invest, risk_level);
		if (risk_level == 2)
			invest = (int) (Math.random() * 985) + 15;
			new Invest(hyip, invest, risk_level);
		invest_money = invest_money + invest;
		hyip.acceptDeposit(invest);
	}

	private Hyip chooseProducer() {
		Context<Player> context = ContextUtils.getContext(this);
		return (Hyip) context.getRandomObjects(Hyip.class, 1)
				.iterator().next();
	}

	public static void reset() {
		for (Object p : CredibilityGame.PLAYERS.getObjects(Investor.class)) {
			((Investor) p).setGain(0);
			((Investor) p).getStrategy().clear();
		}
	}

	public int getExpertise() {
		return expertise;
	}

	public void setExpertise(int expertise) {
		this.expertise = expertise;
	}

	public RatingStrategy getRatingStrategy() {
		return ratingStrategy;
	}

	public void setRatingStrategy(RatingStrategy ratingStrategy) {
		this.ratingStrategy = ratingStrategy;
	}
}
