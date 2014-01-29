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

public class HYIPowner extends Player {
	public static HashMap<String, Double> HONEST_PAYOFFS = new HashMap<String, Double>();
	public static HashMap<String, Double> LIAR_PAYOFFS = new HashMap<String, Double>();
	// public static double PRODUCER_LIAR_RATE;
	// private static int PRODUCER_TYPE_H;
	// private static int PRODUCER_TYPE_L;
	private boolean isHonest;

	private Rating currentRating;
	private Rating pendingRating;

	public static double perc; // oprocentowanie
	int cash;
	static int look; // wygl¹d strony

	int marketing; // 0-basic 1-expert 2-proffesional
	static int e_cost; // marketing cost expert
	static int p_cost; // marketing cost prof
	public static double e_eff; // marketing efect expert
	public static double p_eff; // marketing efect prof
	public static double l_eff; // look efect prof

	public static void initialize() {
		// ArrayList<Double> hpayoffs =
		// Utils.readDoubleList("producer_honest_payoffs");
		// ArrayList<Double> lpayoffs =
		// Utils.readDoubleList("producer_liar_payoffs");
		// for(int i=0; i<Utils.PAYOFFS_KEYS.length; i++){
		// HONEST_PAYOFFS.put(Utils.PAYOFFS_KEYS[i], hpayoffs.get(i));
		// LIAR_PAYOFFS.put(Utils.PAYOFFS_KEYS[i], lpayoffs.get(i));
		// }
		// System.out.println("HONEST_PAYOFFS: "+HONEST_PAYOFFS);
		// System.out.println("LIAR_PAYOFFS: "+LIAR_PAYOFFS);
		Parameters params = RunEnvironment.getInstance().getParameters();
		perc = (Double) params.getValue("hyip_perc");
		// marketing = (int)params.getValue("hyip_marketing");
		look = (Integer) params.getValue("hyip_look");
		e_cost = (Integer) params.getValue("e_cost");
		p_cost = (Integer) params.getValue("p_cost");

		e_eff = (Double) params.getValue("e_eff");
		p_eff = (Double) params.getValue("p_eff");
		l_eff = (Double) params.getValue("l_eff");

		// PRODUCER_TYPE_H = (Integer)params.getValue("producer_type_h");
		// PRODUCER_TYPE_L = (Integer)params.getValue("producer_type_l");
		// PRODUCER_LIAR_RATE = (Double)params.getValue("producer_liar_rate");
	}

	public HYIPowner() {
		// int rnd = RandomHelper.createUniform(PRODUCER_TYPE_L,
		// PRODUCER_TYPE_H).nextInt();//random.nextInt(PRODUCER_TYPE_H)-PRODUCER_TYPE_L;
		// this.isHonest = rnd<=0?false:true;
		// this.isHonest = isHonest;
		// this.currentRating = new UpDownRating();
		// this.pendingRating = this.currentRating.clone();
		// setStrategy(new ProducerStrategy(this));
	}

	public double getAdvert() {
		double adv = look * l_eff;

		switch (marketing) {
		case 1:
			adv += e_eff;
			break;
		case 2:
			adv += p_eff;
			break;
		}
		return adv;

	}

	public void changeMoney(int value) {
		cash = cash + value;
	}

	@ScheduledMethod(start = 1.0, interval = 1.0, priority = 250)
	public void step() {
		// System.out.print(".");
		// ((ProducerStrategy)getStrategy()).generateInformation();

		setMarketing();

	}

	public void setMarketing() {
		double r = Math.random();
		if (r < 0.33) {
			marketing = 0;
			changeMoney(0);
		} else if (r < 0.66) {
			marketing = 1;
			changeMoney(-e_cost);
		} else {
			marketing = 2;
			changeMoney(-p_cost);
		}
	}

	/*
	 * public static void evolve(){ IndexedIterable<Player> allProducers =
	 * CredibilityGame.PLAYERS.getObjects(HYIPowner.class); //ArrayList<Player>
	 * producersListHonest = new ArrayList<Player>(); //ArrayList<Player>
	 * producersListLiar = new ArrayList<Player>(); //for(Object
	 * producer:allProducers){ // if(((HYIPowner)producer).isHonest()) //
	 * producersListHonest.add(((HYIPowner)producer)); // else //
	 * producersListLiar.add(((HYIPowner)producer)); }
	 * //Player.stochasticSampling(producersListHonest);
	 * //Player.stochasticSampling(producersListLiar);
	 * 
	 * Iterable<Player> mutatedProducers =
	 * CredibilityGame.PLAYERS.getRandomObjects(HYIPowner.class,
	 * (int)(allProducers.size()*0.01)); for(Object p:mutatedProducers){
	 * ((ProducerStrategy
	 * )((HYIPowner)p).getStrategy()).setLook(random.nextInt(2)); } }
	 */

	public static void reset() {
		for (Object p : CredibilityGame.PLAYERS.getObjects(HYIPowner.class)) {

			((HYIPowner) p).getStrategy().clear();
		}
	}

	// Methods for the file outputters

	public int getCash() {
		return cash;
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
}
