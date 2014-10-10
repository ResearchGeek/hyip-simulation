package CredibilityGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

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
import HyipGame.HyipTools;

/**
 * Represent a single instance of a Hyip, contains references to investments and
 * have scheduled method for calculating percentage to pay. Also with evolution
 * model and exit strategies.
 * 
 * @author Oskar Jarczyk
 * @since 1.0
 * @version 2.2
 * @update 11.10.2014
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
	private EpPair epPair;
	private double income;
	
	private Boolean frozen;
	private int whenFrozen;

	private ArrayList<HyipOffert> hyipOfferts;
	private Set<Invest> hyipSoldInvestments;
	private PriorityQueue<Double> probablePayouts;
	private static boolean probable_payouts_by_tops;

	private static boolean l_cost_rand;
	private int marketing; // 0-basic 1-expert 2-proffesional
	private double mktg_cumulated; // wzrost albo spadek wydajnosci mktg
	// w zaleznosci od wydatkow w turze
	private static int e_cost; // marketing cost expert
	private static int p_cost; // marketing cost prof
	private static int l_cost; // marketing cost prof
	private static double e_eff; // marketing efect expert
	private static double p_eff; // marketing efect prof
	private static double inv_rec;
	private double x_e_use;
	private double x_p_use;

	public static void initialize() {
		Parameters params = RunEnvironment.getInstance().getParameters();
		e_cost = (Integer) params.getValue("e_cost");
		p_cost = (Integer) params.getValue("p_cost");
		l_cost = (Integer) params.getValue("l_cost");
		l_cost_rand = (Boolean) params.getValue("l_cost_rand");
		probable_payouts_by_tops = (Boolean) params
				.getValue("income_eval_frompeek");
		COUNT_HYIPS = 0;
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
		this.hyipSoldInvestments = Collections.synchronizedSet(new HashSet<Invest>());
		this.frozen = false;
		this.whenFrozen = 0;
		++COUNT_HYIPS;
		id = COUNT_HYIPS;
		x_e_use = RandomHelper.nextDoubleFromTo(0, 1);
		x_p_use = RandomHelper.nextDoubleFromTo(0, 1);
		epPair = new EpPair(HyipTools.interpretEP_use(x_e_use, x_p_use)[0],
				HyipTools.interpretEP_use(x_e_use, x_p_use)[1]);
		hyipStatistics = new HyipStatistics();
		probablePayouts = new PriorityQueue<Double>(
				Constraints.MemoryAllocForQueue, new Comparator<Double>() {
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
				offerts.add(new HyipOffert(HyipTypicalOffert.MEDIUM_RISK_1D));
				break;
			case GOOD_LOOKING_2A:
				offerts.add(new HyipOffert(HyipTypicalOffert.MEDIUM_RISK_7D));
				break;
			case GOOD_LOOKING_3A:
				offerts.add(new HyipOffert(HyipTypicalOffert.LOW_RISK_1D));
				break;
			case GOOD_LOOKING_4A:
				offerts.add(new HyipOffert(HyipTypicalOffert.LOW_RISK_7D));
				break;
			default:
				break;
			}
		} else {
			switch (badLooking) {
			case BAD_LOOKING_1B:
				offerts.add(new HyipOffert(HyipTypicalOffert.HIGH_RISK_1D));
				break;
			case BAD_LOOKING_2B:
				offerts.add(new HyipOffert(HyipTypicalOffert.HIGH_RISK_7D));
				break;
			case BAD_LOOKING_3B:
				offerts.add(new HyipOffert(HyipTypicalOffert.MEDIUM_RISK_1D));
				break;
			case BAD_LOOKING_4B:
				offerts.add(new HyipOffert(HyipTypicalOffert.MEDIUM_RISK_7D));
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
	
	public String getTypicalPercentage() {
		return hyipOfferts.get(0).getPercent() * 100 + "%";
	}
	
	public String getOffertType() {
		return hyipOfferts.get(0).getForHowLong() >= 6 ? "w" : "d";
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

	@ScheduledMethod(start = 2.0, interval = 1.0, priority = 500)
	public synchronized void considerRunningAway() {
		if (getGameController().isFirstGeneration()) {
			firstRoundAnalysis();
		} else if (!getFrozen()) {
			if (getGameController().isWarmedUp()) {
				logActivity(Constraints.CONSIDERING_RUNNING_AWAY);
				boolean runaway = ExitStrategyUtilities.checkForPass(this);
				if (runaway) {
					say("HYIP " + this.id + " decided to RUN away!");
					freezeHyip(getGameController().getCurrentIteration());
				} else {
					logActivity("Hyip " + this.id + " decides to stay more.");
				}
			}
		}
	}

	@ScheduledMethod(start = 1.0, interval = 1.0, priority = 250)
	public synchronized void payPercent() {
		if (!getFrozen()) {
			for (Iterator<Invest> i = hyipSoldInvestments.iterator(); i.hasNext();) {
				Invest invest = i.next();
				invest.incrementTickCount();
				invest.calculateInterest();
				if (invest.getTickCount() >= invest.getHyipOffert()
						.getForHowLong()) {
					if (RandomHelper.nextDoubleFromTo(0, 1) > inv_rec) {
						// keeping the money in hyip - investor decided to renew
						invest.setTickCount(0);
					} else {
						// close and pay to client
						//hyipSoldInvestments.remove(invest);
						i.remove();
						transferFunds(invest);
						// invest moved to archived
						// hopefully later garbage collected
					}
				}
			}
		}
	}

	@ScheduledMethod(start = 1.0, interval = 1.0, priority = 200)
	public synchronized void resetIncome() {
		hyipAccount.setIncome(0);
	}

	@ScheduledMethod(start = 1.0, interval = 1.0, priority = 10)
	public void step() {
		if (!getFrozen()) {
			logActivity("The HYIP " + this.id
					+ " is considering it's marketing");
			setMarketing();
		}
	}

	@ScheduledMethod(start = 1.0, interval = 1.0, priority = 5)
	public void calculateIncome() {
		if (!getFrozen()) {
			logActivity("The HYIP " + this.id + " is calculating its income");
			//this.income = hyipAccount.getIncome() - propablePayouts();
			this.income = hyipAccount.getIncome();
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

	private void freezeHyip(Integer day) {
		// running away to Bahamas, HYIPs stops from showing any signs of life
		this.setFrozen(true);
		this.setWhenFrozen(day);
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
		if (probable_payouts_by_tops) {
			for (int i = 0; i < (int) (probablePayouts.size() * inv_rec); i++) {
				result += probablePayouts.poll();
			}
		} else {
			for (int i = 0; i < probablePayouts.size(); i++) {
				result += probablePayouts.poll();
			}
		}
		return result;
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
		if (r > getE_use() + getP_use()) {  // without marketing
			marketing = 0;
			hyipAccount.addMoney(0);
		} else if (r < getE_use()) {  // marketing on "expert" level (middle)
			marketing = 1;
			hyipAccount.addMoney(-e_cost);
		} else {  // marketing on professional level (highest)
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
	
	public double getBalance() {
		return getCash();
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
		exitStrategy
				.setBalance(exitStrategy.getBalance()
						+ (exitStrategy.getBalance() * RandomHelper
								.nextDoubleFromTo(-Constraints.MUTATE_FACTOR,
										Constraints.MUTATE_FACTOR)));
		exitStrategy
				.setIncome(exitStrategy.getIncome()
						+ (exitStrategy.getIncome() * RandomHelper
								.nextDoubleFromTo(-Constraints.MUTATE_FACTOR,
										Constraints.MUTATE_FACTOR)));
		exitStrategy.setInvestorCount(exitStrategy.getInvestorCount()
				+ (int) (exitStrategy.getInvestorCount() * RandomHelper
						.nextDoubleFromTo(-Constraints.MUTATE_FACTOR,
								Constraints.MUTATE_FACTOR)));
		exitStrategy.setTime(exitStrategy.getTime()
				+ (int) (exitStrategy.getTime() * RandomHelper
						.nextDoubleFromTo(-Constraints.MUTATE_FACTOR,
								Constraints.MUTATE_FACTOR)));

		say("E_use of hyip no." + getId() + " before mutation: " + getE_use());
		say("P_use of hyip no." + getId() + " before mutation: " + getP_use());

		setE_use(getE_use()
				+ (this.getE_use() * RandomHelper.nextDoubleFromTo(
						-Constraints.MUTATE_FACTOR, Constraints.MUTATE_FACTOR)));
		setP_use(getP_use()
				+ (this.getP_use() * RandomHelper.nextDoubleFromTo(
						-Constraints.MUTATE_FACTOR, Constraints.MUTATE_FACTOR)));
		if (getE_use() + getP_use() > 1.0) {
			double normalEP[] = HyipTools.normalizeEP(getE_use(), getP_use());
			setE_use(normalEP[0]);
			setP_use(normalEP[1]);
		}

		say("E_use of hyip no." + getId() + " after mutation: " + getE_use());
		say("P_use of hyip no." + getId() + " after mutation: " + getP_use());

		ExitStrategyOptions ops = exitStrategy.getExitStrategyOptions();
		if (RandomHelper.nextDoubleFromTo(0, 1) <= Constraints.MUTATE_CHANCE) {
			ops.setConsiderBalance(!ops.isConsiderBalance().booleanValue());
		}
		if (RandomHelper.nextDoubleFromTo(0, 1) <= Constraints.MUTATE_CHANCE) {
			ops.setConsiderIncome(!ops.isConsiderIncome().booleanValue());
		}
		if (RandomHelper.nextDoubleFromTo(0, 1) <= Constraints.MUTATE_CHANCE) {
			ops.setConsiderInvestorCount(!ops.isConsiderInvestorCount()
					.booleanValue());
		}
		if (RandomHelper.nextDoubleFromTo(0, 1) <= Constraints.MUTATE_CHANCE) {
			ops.setConsiderTime(!ops.isConsiderTime().booleanValue());
		}
	}

	public Boolean getFrozen() {
		return frozen;
	}

	public void setFrozen(Boolean frozen) {
		this.frozen = frozen;
	}
	
	public Integer getWhenFrozen() {
		return whenFrozen;
	}

	public void setWhenFrozen(Integer whenFrozen) {
		this.whenFrozen = whenFrozen;
	}

	public double getE_use() {
		return this.epPair.getE_use();
	}

	public double getP_use() {
		return this.epPair.getP_use();
	}

	public double getX_e_use() {
		return x_e_use;
	}

	public void setX_e_use(double x_e_use) {
		this.x_e_use = x_e_use;
	}

	public EpPair getEpPair() {
		return epPair;
	}

	public void setEpPair(EpPair epPair) {
		this.epPair = epPair;
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

	public void setE_use(double e_use) {
		this.epPair.setE_use(e_use);
	}

	public void setP_use(double p_use) {
		this.epPair.setP_use(p_use);
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
		return getGameController().getCurrentIteration() + 1;
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
