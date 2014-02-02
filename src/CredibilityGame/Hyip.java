package CredibilityGame;

import java.util.ArrayList;
import java.util.HashMap;
import CredibilityGame.rating.Rating;
import CredibilityGame.rating.UpDownRating;
import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.util.ContextUtils;
import repast.simphony.util.collections.IndexedIterable;

public class Hyip extends Player {
	
	// ********************* Credibility game variables ***********************
	public static HashMap<String, Double> HONEST_PAYOFFS = 
			new HashMap<String, Double>();
	public static HashMap<String, Double> LIAR_PAYOFFS = 
			new HashMap<String, Double>();
	public static double PRODUCER_LIAR_RATE;
	private static int PRODUCER_TYPE_H;
	private static int PRODUCER_TYPE_L;
	private boolean isHonest;
	private Rating currentRating;
	private Rating pendingRating;
	// ********************* End of credibility game variables ***************

	private HyipAccount hyipAccount;
	private ArrayList<HyipOffert> hyipOfferts;
	
	public static double perc; // oprocentowanie
	static int look; // wygl¹d strony
	int marketing; // 0-basic 1-expert 2-proffesional
	double mktg_cumulated; // wzrost albo spadek wydajnosci mktg w zaleznosci od
							// wydatkow w turze
	static int e_cost; // marketing cost expert
	static int p_cost; // marketing cost prof
	static int l_cost; // marketing cost prof
	public static double e_eff; // marketing efect expert
	public static double p_eff; // marketing efect prof
	public static double l_eff; // look efect prof
	public static double e_use;
	public static double p_use;

	public static void initialize() {

		Parameters params = RunEnvironment.getInstance().getParameters();
		perc = (Double) params.getValue("hyip_perc");
		e_use = (double) params.getValue("e_use");
		p_use = (double) params.getValue("p_use");
		look = (Integer) params.getValue("hyip_look");
		e_cost = (Integer) params.getValue("e_cost");
		p_cost = (Integer) params.getValue("p_cost");
		l_cost = (Integer) params.getValue("l_cost");

		e_eff = (Double) params.getValue("e_eff");
		p_eff = (Double) params.getValue("p_eff");
		l_eff = (Double) params.getValue("l_eff");

		// PRODUCER_TYPE_H = (Integer)params.getValue("producer_type_h");
		// PRODUCER_TYPE_L = (Integer)params.getValue("producer_type_l");
		// PRODUCER_LIAR_RATE = (Double)params.getValue("producer_liar_rate");
	}

	public Hyip() {
		// int rnd = RandomHelper.createUniform(PRODUCER_TYPE_L,
		// PRODUCER_TYPE_H).nextInt();//random.nextInt(PRODUCER_TYPE_H)-PRODUCER_TYPE_L;
		// this.isHonest = rnd<=0?false:true;
		// this.isHonest = isHonest;
		// this.currentRating = new UpDownRating();
		// this.pendingRating = this.currentRating.clone();
		// setStrategy(new ProducerStrategy(this));
		this.hyipAccount = new HyipAccount(this, 0 - l_cost);
		this.hyipOfferts = createOfferts();
	}
	
	private ArrayList<HyipOffert> createOfferts(){
		ArrayList<HyipOffert> offerts = new ArrayList<HyipOffert>();
		offerts.add(HyipOffert.TypicalHyipOffer.LOW_RISK);
		offerts.add(HyipOffert.TypicalHyipOffer.MEDIUM_RISK);
		offerts.add(HyipOffert.TypicalHyipOffer.HIGH_RISK);
		return offerts;
	}
	
	public HyipOffert getOffert(int i){
		return hyipOfferts.get(i);
	}

	public double getAdvert() {
		double mark_temp = mktg_cumulated * 12 - 6;
		double adv = look * l_eff
				+ (1 / (1 + Math.pow(Math.E, mark_temp * (-1)))) * l_eff;
		if (adv > 1)
			adv = 1;
		return adv;

	}

	@ScheduledMethod(start = 1.0, interval = 1.0, priority = 250)
	public void step() {
		setMarketing();
	}

	public void setMarketing() {
		double r = Math.random();
		if (r > e_use + p_use) {
			marketing = 0;
			hyipAccount.addMoney(0);
		} else if (r < e_use) {
			marketing = 1;
			hyipAccount.addMoney(-e_cost);
		} else {
			marketing = 2;
			hyipAccount.addMoney(-p_cost);
		}
		switch (marketing) {
			case 0:
				mktg_cumulated -= e_eff;
				break;
			case 1:
				mktg_cumulated += e_eff;
				break;
			case 2:
				mktg_cumulated += p_eff;
				break;
		}
		if (mktg_cumulated < 0)
			mktg_cumulated = 0;
		if (mktg_cumulated > 1)
			mktg_cumulated = 1;
	}

	public static void reset() {
		for (Object p : CredibilityGame.PLAYERS.getObjects(Hyip.class)) {
			((Hyip) p).getStrategy().clear();
		}
	}

	public double getCash() {
		return hyipAccount.getCash();
	}

	public String getStrategyAsString() {
		return getStrategy().toString();
	}

	public int getCurrentIteration() {
		Context<Object> context = ContextUtils.getContext(this);
		Context<Object> parentContext = ContextUtils.getParentContext(context);
		GameController controller = (GameController) parentContext.getObjects(
				GameController.class).get(0);
		return controller.getCurrentIteration();
	}

	public int getCurrentGeneration() {
		Context<Object> context = ContextUtils.getContext(this);
		Context<Object> parentContext = ContextUtils.getParentContext(context);
		GameController controller = (GameController) parentContext.getObjects(
				GameController.class).get(0);
		return controller.getCurrentGeneration();
	}

	public void resetReputation() {
		setCurrentRating(new UpDownRating());
		setPendingRating(getCurrentRating().clone());
	}

	public Rating getCurrentRating() {
		return currentRating;
	}

	public void setCurrentRating(Rating currentRating) {
		this.currentRating = currentRating;
	}

	public Rating getPendingRating() {
		return pendingRating;
	}

	public void setPendingRating(Rating pendingRating) {
		this.pendingRating = pendingRating;
	}

	public boolean isHonest() {
		return isHonest;
	}

	public void setHonest(boolean isHonest) {
		this.isHonest = isHonest;
	}

	public static int getProducerTypeH() {
		return PRODUCER_TYPE_H;
	}

	public static int getProducerTypeL() {
		return PRODUCER_TYPE_L;
	}

	public void makeWithdrawal(int invest_money) {
		hyipAccount.addMoney(-invest_money);
	}

	public void acceptDeposit(int invest) {
		hyipAccount.addMoney(invest);
	}
}
