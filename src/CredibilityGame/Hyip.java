package CredibilityGame;

import java.util.ArrayList;
import java.util.HashMap;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.util.ContextUtils;
import CredibilityGame.HyipType.BadLooking;
import CredibilityGame.HyipType.GoodLooking;
import CredibilityGame.rating.Rating;
import CredibilityGame.rating.UpDownRating;

public class Hyip extends Player {

	// ********************* Credibility game variables ***********************
	public static HashMap<String, Double> HONEST_PAYOFFS = new HashMap<String, Double>();
	public static HashMap<String, Double> LIAR_PAYOFFS = new HashMap<String, Double>();
	public static double PRODUCER_LIAR_RATE;
	private static int PRODUCER_TYPE_H;
	private static int PRODUCER_TYPE_L;
	private boolean isHonest;
	private Rating currentRating;
	private Rating pendingRating;
	// ********************* End of credibility game variables ***************

	private HyipAccount hyipAccount;
	private ArrayList<HyipOffert> hyipOfferts;

	private static boolean l_cost_rand;
	private static int look; // wyglad strony
	private int marketing; // 0-basic 1-expert 2-proffesional
	private double mktg_cumulated; // wzrost albo spadek wydajnosci mktg
									// w zaleznosci od wydatkow w turze
	private static int e_cost; // marketing cost expert
	private static int p_cost; // marketing cost prof
	private static int l_cost; // marketing cost prof
	private static double e_eff; // marketing efect expert
	private static double p_eff; // marketing efect prof
	private static double l_eff; // look efect prof
	private static double e_use;
	private static double p_use;

	public static void initialize() {
		Parameters params = RunEnvironment.getInstance().getParameters();
		//perc = (Double) params.getValue("hyip_perc");
		e_use = (double) params.getValue("e_use");
		p_use = (double) params.getValue("p_use");
		look = (Integer) params.getValue("hyip_look");
		e_cost = (Integer) params.getValue("e_cost");
		p_cost = (Integer) params.getValue("p_cost");
		l_cost = (Integer) params.getValue("l_cost");
		l_cost_rand = (Boolean) params.getValue("l_cost_rand");

		e_eff = (Double) params.getValue("e_eff");
		p_eff = (Double) params.getValue("p_eff");
		l_eff = (Double) params.getValue("l_eff");
	}

	@Deprecated
	public Hyip() {
		throw new UnsupportedOperationException("Initialize with enum!");
		//this.hyipAccount = new HyipAccount(this, 0 - l_cost);
		//this.hyipOfferts = createOfferts();
	}

	public Hyip(GoodLooking goodLooking) {
		l_cost = l_cost_rand ? RandomHelper.nextIntFromTo(1750, 3000) : l_cost;
		this.hyipAccount = new HyipAccount(this, 0 - l_cost);
		this.hyipOfferts = createOfferts(true, goodLooking, null);
	}

	public Hyip(BadLooking badLooking) {
		l_cost = l_cost_rand ? RandomHelper.nextIntFromTo(500, 1749) : l_cost;
		this.hyipAccount = new HyipAccount(this, 0 - l_cost);
		this.hyipOfferts = createOfferts(false, null, badLooking);
	}

	private ArrayList<HyipOffert> createOfferts(boolean isGoodLooking,
			GoodLooking goodLooking, BadLooking badLooking) {
		ArrayList<HyipOffert> offerts = new ArrayList<HyipOffert>();
		if (isGoodLooking) {
			switch (goodLooking) {
			case GOOD_LOOKING_1A:
				offerts.add(HyipTypicalOffert.MEDIUM_RISK_1D);
				break;
			case GOOD_LOOKING_2A:
				offerts.add(HyipTypicalOffert.MEDIUM_RISK_7D);
				break;
			case GOOD_LOOKING_3A:
				offerts.add(HyipTypicalOffert.LOW_RISK_1D);
				break;
			case GOOD_LOOKING_4A:
				offerts.add(HyipTypicalOffert.LOW_RISK_7D);
				break;
			default:
				break;
			}
		} else {
			switch (badLooking) {
			case BAD_LOOKING_1B:
				offerts.add(HyipTypicalOffert.HIGH_RISK_1D);
				break;
			case BAD_LOOKING_2B:
				offerts.add(HyipTypicalOffert.HIGH_RISK_7D);
				break;
			case BAD_LOOKING_3B:
				offerts.add(HyipTypicalOffert.MEDIUM_RISK_1D);
				break;
			case BAD_LOOKING_4B:
				offerts.add(HyipTypicalOffert.MEDIUM_RISK_7D);
				break;
			default:
				break;
			}
		}
		return offerts;
	}

	public HyipOffert getOffert(int i) {
		return hyipOfferts.get(i);
	}
	
	public HyipOffert getFirstOffert() {
		return hyipOfferts.get(0);
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
		if (r > e_use + p_use) { // bez marketingu
			marketing = 0;
			hyipAccount.addMoney(0);
		} else if (r < e_use) {// marketing na poziomie expert (srednim)
			marketing = 1;
			hyipAccount.addMoney(-e_cost);
		} else {// marketing na poziomie proffessional (najwyzszym)
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
