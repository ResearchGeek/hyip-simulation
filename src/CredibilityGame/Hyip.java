package CredibilityGame;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import logger.PjiitOutputter;
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
import HyipGame.ExitStrategy;
import HyipGame.ExitStrategyOptions;
import HyipGame.ExitStrategyUtilities;
import HyipGame.HyipStatistics;

/**
 * Represent a single instance of a Hyip, contains references to investments and
 * have scheduled method for calculating percentage to pay. Also with evolution
 * model and exit strategies.
 * 
 * @author Oskar Jarczyk
 * @since 1.0
 * @version 1.2
 */
public class Hyip extends Player {

	// ********************* Credibility game variables ************************
	public static HashMap<String, Double> HONEST_PAYOFFS = new HashMap<String, Double>();
	// not part of a hyip game
	public static HashMap<String, Double> LIAR_PAYOFFS = new HashMap<String, Double>();
	// not part of hyip game
	public static double PRODUCER_LIAR_RATE; // not part of hyip game
	private static int PRODUCER_TYPE_H; // not part of hyip game
	private static int PRODUCER_TYPE_L; // not part of hyip game
	private boolean isHonest; // not part of hyip game
	private Rating currentRating; // not part of hyip game
	private Rating pendingRating; // not part of hyip game
	// ********************* End of credibility game variables *****************

	private static volatile long COUNT_HYIPS = 0;
	private static GameController gameController;

	private long totalNumberOfInvestments = 0;
	private Long id;
	private boolean isGoodLooking;
	private ExitStrategy exitStrategy;
	private HyipStatistics hyipStatistics;
	private HyipAccount hyipAccount;
	private double income;
	private Boolean frozen;

	private ArrayList<HyipOffert> hyipOfferts;
	private volatile CopyOnWriteArrayList<Invest> hyipSoldInvestments;
	private PriorityQueue<Double> probablePayouts;

	private static boolean l_cost_rand;
	private int marketing; // 0-basic 1-expert 2-proffesional
	private double mktg_cumulated; // wzrost albo spadek wydajnosci mktg
	// w zaleznosci od wydatkow w turze
	private static int e_cost; // marketing cost expert
	private static int p_cost; // marketing cost prof
	private static int l_cost; // marketing cost prof
	private static double e_eff; // marketing efect expert
	private static double p_eff; // marketing efect prof
	// private static double l_eff; // look efect prof
	private static double e_use;
	private static double p_use;
	private static double inv_rec;

	public static void initialize() {
		Parameters params = RunEnvironment.getInstance().getParameters();
		e_use = (double) params.getValue("e_use");
		p_use = (double) params.getValue("p_use");
		e_cost = (Integer) params.getValue("e_cost");
		p_cost = (Integer) params.getValue("p_cost");
		l_cost = (Integer) params.getValue("l_cost");
		l_cost_rand = (Boolean) params.getValue("l_cost_rand");

		e_eff = (Double) params.getValue("e_eff");
		p_eff = (Double) params.getValue("p_eff");
		inv_rec = (Double) params.getValue("inv_rec");
	}

	public Hyip(boolean isGoodLooking, int costFrom, int costTo,
			Object goodOrBad) {
		l_cost = l_cost_rand ? RandomHelper.nextIntFromTo(costFrom, costTo)
				: l_cost;
		this.isGoodLooking = isGoodLooking;
		this.hyipAccount = new HyipAccount(this, 0 - l_cost);
		this.hyipOfferts = isGoodLooking ? createOfferts(
				(GoodLooking) goodOrBad, null) : createOfferts(null,
				(BadLooking) goodOrBad);
		this.hyipSoldInvestments = new CopyOnWriteArrayList<Invest>();
		this.frozen = false;
		++COUNT_HYIPS;
		id = COUNT_HYIPS;
		hyipStatistics = new HyipStatistics();
		probablePayouts = new PriorityQueue<Double>(10000,
				new Comparator<Double>() {
					public int compare(Double o1, Double o2) {
						return -o1.compareTo(o2);
					}
				});
	}

	public GameController initGameController() {
		Context<Object> context = ContextUtils.getContext(this);
		Context<Object> parentContext = ContextUtils.getParentContext(context);
		gameController = (GameController) parentContext.getObjects(
				GameController.class).get(0);
		return gameController;
	}

	public Hyip(GoodLooking goodLooking) {
		this(true, 1750, 3000, goodLooking);
	}

	public Hyip(BadLooking badLooking) {
		this(false, 500, 1749, badLooking);
	}

	private ArrayList<HyipOffert> createOfferts(GoodLooking goodLooking,
			BadLooking badLooking) {
		ArrayList<HyipOffert> offerts = new ArrayList<HyipOffert>();
		if (this.isGoodLooking) {
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
		double adv = 1 / (1 + Math.pow(Math.E, mark_temp * (-1)));
		if (adv > 1)
			adv = 1;
		assert adv >= 0;
		assert adv <= 1;
		return adv;
	}

	public String describeAdvert() {
		return (Constraints.DECIMAL_SHORT.format(getAdvert()));
	}

	public Long getId() {
		return id;
	}

	@ScheduledMethod(start = 1.0, interval = 1.0, priority = 250)
	public void step() {
		if (!getFrozen()) {
			logActivity("The HYIP " + this.id
					+ " is considering it's marketing");
			setMarketing();
		}
	}

	@ScheduledMethod(start = 2.0, interval = 1.0, priority = 5)
	public synchronized void considerRunningAway() {
		if (getGameController().isFirstGeneration()) {
			firstRoundAnalysis();
		} else if (!getFrozen()) {
			logActivity("The HYIP " + this.id + " is calculating its income");
			this.income = hyipAccount.getIncome() - propablePayouts();
			if (getGameController().isWarmedUp()) {
				logActivity(Constraints.CONSIDERING_RUNNING_AWAY);
				boolean runaway = ExitStrategyUtilities.checkForPass(this);
				if (runaway) {
					say("HYIP " + this.id + " decided to RUN awaay!");
					freezeHyip();
				} else {
					logActivity("Hyip " + this.id + " decides to stay more.");
				}
			}
		}
	}

	private void firstRoundAnalysis() {
		if (hyipStatistics.isBetterMoment(hyipAccount.getCash())) {
			hyipStatistics.setCash(hyipAccount.getCash());
			hyipStatistics.setIncome(getIncome());
			hyipStatistics.setInvestorCount(countOngoingInvestments());
			hyipStatistics.setTick(getIteration());
			exitStrategy.updateFromStats(hyipStatistics, true);
		}
	}

	private void freezeHyip() {
		// running away to Bahamas, HYIPs stops from showing any signs of life
		this.frozen = true;
		// but still in context
	}

	private synchronized double propablePayouts() {
		double result = 0;
		probablePayouts.clear();
		for (Invest invest : hyipSoldInvestments) {
			if (invest.getTickCount() + 1 >= invest.getHyipOffert()
					.getForHowLong()) {
				probablePayouts.add(invest.forecastInterest());
			}
		}
		Iterator<Double> it = probablePayouts.iterator();
		for (int i = 0; i < probablePayouts.size() * inv_rec; i++) {
			result += it.next();
		}
		return result;
	}

	@ScheduledMethod(start = 1.0, interval = 1.0, priority = 10)
	public synchronized void payPercent() {
		if (!getFrozen()) {
			hyipAccount.setIncome(0);
			for (Invest invest : hyipSoldInvestments) {
				invest.incrementTickCount();
				invest.calculateInterest();
				if (invest.getTickCount() >= invest.getHyipOffert()
						.getForHowLong()) {
					if (RandomHelper.nextDoubleFromTo(0, 1) > inv_rec) {
						// keeping the money in hyip - investor decided to renew
						invest.setTickCount(0);
					} else {
						// close and pay to client
						hyipSoldInvestments.remove(invest);
						transferFunds(invest);
						// invest moved to archived
						// hopefully later garbage collected
					}
				}
			}
		}
	}

	public double getIncome() {
		return this.income;
	}

	public int countOngoingInvestments() {
		return hyipSoldInvestments.size();
	}

	private void transferFunds(Invest invest) {
		double money = invest.getMoney();
		this.hyipAccount.withdrawMoney(money);
		invest.getInvestor().acceptReward(money);
	}

	public void registerInvestment(Invest invest) {
		hyipSoldInvestments.add(invest);
		setTotalNumberOfInvestments(totalNumberOfInvestments + 1);
		double money = invest.getMoney();
		acceptDeposit(money);
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

	public void resetMe() {
		this.hyipAccount.clear();
		this.income = 0;
		this.frozen = false;
		this.totalNumberOfInvestments = 0;
		this.mktg_cumulated = 0;
		this.hyipSoldInvestments.clear();
		this.hyipStatistics.clear();
	}

	public static void calculateRois() {
		say(Constraints.CALCULATE_ROIS_EXECUTED);
	}

	public double getCash() {
		return hyipAccount.getCash();
	}

	public long getTotalNumberOfInvestments() {
		return totalNumberOfInvestments;
	}

	public void setTotalNumberOfInvestments(long totalNumberOfInvestments) {
		this.totalNumberOfInvestments = totalNumberOfInvestments;
	}

	public String getStrategyAsString() {
		return getStrategy().toString();
	}

	public GameController getGameController() {
		return gameController == null ? initGameController() : gameController;
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

	private void acceptDeposit(double invest) {
		hyipAccount.addMoney(invest);
	}

	public boolean isGoodLooking() {
		return isGoodLooking;
	}

	public ExitStrategy getExitStrategy() {
		return exitStrategy;
	}

	public void setExitStrategy(ExitStrategy exitStrategy) {
		this.exitStrategy = exitStrategy;
	}

	public void mutate() {
		ExitStrategyOptions ops = exitStrategy.getExitStrategyOptions();
		if (RandomHelper.nextIntFromTo(0, 100) <= Constraints.MUTATE_CHANCE) {
			ops.setConsiderBalance(!ops.isConsiderBalance().booleanValue());
		}
		if (RandomHelper.nextIntFromTo(0, 100) <= Constraints.MUTATE_CHANCE) {
			ops.setConsiderIncome(!ops.isConsiderIncome().booleanValue());
		}
		if (RandomHelper.nextIntFromTo(0, 100) <= Constraints.MUTATE_CHANCE) {
			ops.setConsiderInvestorCount(!ops.isConsiderInvestorCount()
					.booleanValue());
		}
		if (RandomHelper.nextIntFromTo(0, 100) <= Constraints.MUTATE_CHANCE) {
			ops.setConsiderTime(!ops.isConsiderTime().booleanValue());
		}
		if (RandomHelper.nextIntFromTo(0, 100) <= Constraints.MUTATE_CHANCE) {
			exitStrategy.setBalance(exitStrategy.getBalance()
					+ (exitStrategy.getBalance() * RandomHelper
							.nextDoubleFromTo(-Constraints.MUTATE_FACTOR,
									Constraints.MUTATE_FACTOR)));
		}
		if (RandomHelper.nextIntFromTo(0, 100) <= Constraints.MUTATE_CHANCE) {
			exitStrategy.setIncome(exitStrategy.getIncome()
					+ (exitStrategy.getIncome() * RandomHelper
							.nextDoubleFromTo(-Constraints.MUTATE_FACTOR,
									Constraints.MUTATE_FACTOR)));
		}
		if (RandomHelper.nextIntFromTo(0, 100) <= Constraints.MUTATE_CHANCE) {
			exitStrategy.setInvestorCount(exitStrategy.getInvestorCount()
					+ (int) (exitStrategy.getInvestorCount() * RandomHelper
							.nextDoubleFromTo(-Constraints.MUTATE_FACTOR,
									Constraints.MUTATE_FACTOR)));
		}
		if (RandomHelper.nextIntFromTo(0, 100) <= Constraints.MUTATE_CHANCE) {
			exitStrategy.setTime(exitStrategy.getTime()
					+ (int) (exitStrategy.getTime() * RandomHelper
							.nextDoubleFromTo(-Constraints.MUTATE_FACTOR,
									Constraints.MUTATE_FACTOR)));
		}
	}

	public Boolean getFrozen() {
		return frozen;
	}

	public void setFrozen(Boolean frozen) {
		this.frozen = frozen;
	}

	public String describeStrategy() {
		StringBuilder sb = new StringBuilder(Constraints.OPENING_BRACKET);
		ExitStrategyOptions ops = exitStrategy.getExitStrategyOptions();

		sb.append("balance:");
		sb.append(ops.isConsiderBalance());
		sb.append(Constraints.COMMA);
		sb.append(exitStrategy.getBalance());
		sb.append(Constraints.SEPERATOR);
		sb.append("income:");
		sb.append(ops.isConsiderIncome());
		sb.append(Constraints.COMMA);
		sb.append(exitStrategy.getIncome());
		sb.append(Constraints.SEPERATOR);
		sb.append("time:");
		sb.append(ops.isConsiderTime());
		sb.append(Constraints.COMMA);
		sb.append(exitStrategy.getTime());
		sb.append(Constraints.SEPERATOR);
		sb.append("investor_count:");
		sb.append(ops.isConsiderInvestorCount());
		sb.append(Constraints.COMMA);
		sb.append(exitStrategy.getInvestorCount());

		sb.append(Constraints.CLOSING_BRACKET);
		return sb.toString();
	}

	public boolean usesBalance() {
		return exitStrategy.getExitStrategyOptions().isConsiderBalance();
	}

	public boolean usesIncome() {
		return exitStrategy.getExitStrategyOptions().isConsiderIncome();
	}

	public boolean usesInvestorCount() {
		return exitStrategy.getExitStrategyOptions().isConsiderInvestorCount();
	}

	public boolean usesTime() {
		return exitStrategy.getExitStrategyOptions().isConsiderTime();
	}

	public double getStrategyBalance() {
		return exitStrategy.getBalance();
	}

	public double getStrategyIncome() {
		return exitStrategy.getIncome();
	}

	public int getStrategyInvestorCount() {
		return exitStrategy.getInvestorCount();
	}

	public int getStrategyTime() {
		return exitStrategy.getTime();
	}

	public int getIteration() {
		return getGameController().getCurrentIteration();
	}

	public int getGeneration() {
		return getGameController().getCurrentGeneration() + 1;
	}

	private void logActivity(String s) {
		PjiitOutputter.log(s);
	}

	private static void say(String s) {
		PjiitOutputter.say(s);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (((Hyip) obj).id.equals(this.id)) {
			return true;
		} else
			return false;
	}
}
