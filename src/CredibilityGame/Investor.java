package CredibilityGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.util.ContextUtils;
import CredibilityGame.rating.strategy.RatingStrategy;

public class Investor extends Player {

	public static HashMap<String, Double> PAYOFFS = new HashMap<String, Double>();
	private static int CONSUMER_TYPE_H;
	private static int CONSUMER_TYPE_L;
	private InvestorType risk_level;
	private InvestorAccount investorAccount; // do zmiany
	private double inv_invest;
	private double inv_rec;
	private int expertise;
	private RatingStrategy ratingStrategy;

	public Investor(InvestorType investorType) {
		this.expertise = RandomHelper.createUniform(CONSUMER_TYPE_L,
				CONSUMER_TYPE_H).nextInt();
		Parameters params = RunEnvironment.getInstance().getParameters();
		inv_invest = (Double) params.getValue("inv_invest");
		inv_rec = (Double) params.getValue("inv_rec");
		risk_level = investorType;
	}

	public void initializeWallet() {
		if (investorAccount != null) {
			throw new UnsupportedOperationException(
					"Agent (investor) already charged his account!");
		} else {
			investorAccount = new InvestorAccount(this);
			switch (risk_level) {
			case HIGH_AVERSION:
				investorAccount.setBalance(50 * 5);
				break;
			case MEDIUM_AVERSION:
				investorAccount.setBalance(50 * 50);
				break;
			case LOW_AVERSION:
				investorAccount.setBalance(50 * 1000);
				break;
			}
		}
	}

	@ScheduledMethod(start = 1.0, interval = 1.0, priority = 100)
	public void step() {
		List<Hyip> hyipsChosen = new ArrayList<Hyip>();
		List<Hyip> allHyips = chooseAllProducers();
		for (Hyip hyip : allHyips) {
			if (investorAccount.hasMoney()) {
				int invChances = 0;
				switch (this.risk_level) {
				case HIGH_AVERSION:
					invChances = hyip.isGoodLooking() ? 90 : 10;
					break;
				case MEDIUM_AVERSION:
					invChances = hyip.isGoodLooking() ? 90 : 30;
					break;
				case LOW_AVERSION:
					invChances = hyip.isGoodLooking() ? 90 : 50;
					break;
				default:
					throw new UnsupportedOperationException(
							"should never happen");
				}
				if (!(RandomHelper.nextDoubleFromTo(0, 100) < invChances)){
					// tests failed
					break;
				}
				if (RandomHelper.nextDoubleFromTo(0, 100) < hyip.getAdvert()){
					// test passed
					hyipsChosen.add(hyip);
				}
			}
		}
		for (Hyip hyip : hyipsChosen){
			chooseOffert(hyip);
		}
	}

	// public void step() {
	// if (investorAccount.hasMoney()) {
	// int howManyInvests = RandomHelper.nextIntFromTo(0, 5);
	// // (inwestr losuje, ile inwestycji chce dokonac w danym ticku)
	// for (int i = 0; i < howManyInvests; i++) {
	// // losuje 3 hyip-y, z ktorych wybiera ten z najlepszym
	// // marketingiem
	// List<Hyip> hyipsConsiderated = chooseProducers(3);
	// Hyip chosen = hyipsConsiderated.get(0);
	// // teraz wybierz ten z najlepszym marketingiem
	// for (Hyip hyip : hyipsConsiderated) {
	// if (hyip.getAdvert() > chosen.getAdvert())
	// chosen = hyip;
	// }
	// chooseOffert(chosen);
	// }
	// }
	// }

	private void chooseOffert(Hyip hyip) {
		invest(hyip, hyip.getFirstOffert());
	}

	private void invest(Hyip hyip, HyipOffert hyipOffert) {
		int invest = 0;
		Invest investment = null;
		if (risk_level == InvestorType.HIGH_AVERSION) {
			invest = (int) (Math.random() * 4) + 1;
			investment = new Invest(this, hyip, invest, hyipOffert);
		} else if (risk_level == InvestorType.MEDIUM_AVERSION) {
			invest = (int) (Math.random() * 45) + 5;
			investment = new Invest(this, hyip, invest, hyipOffert);
		} else if (risk_level == InvestorType.LOW_AVERSION) {
			invest = (int) (Math.random() * 950) + 50;
			investment = new Invest(this, hyip, invest, hyipOffert);
		}
		investorAccount.spendMoney(invest);
		hyip.registerInvestment(investment);
	}

	public void acceptReward(double moneyTransfer) {
		this.investorAccount.addFunds(moneyTransfer);
	}

	private Hyip chooseProducer() {
		Context<Player> context = ContextUtils.getContext(this);
		return (Hyip) context.getRandomObjects(Hyip.class, 1).iterator().next();
	}

	private List<Hyip> chooseProducers(int howMany) {
		Context<Player> context = ContextUtils.getContext(this);
		Iterable<Player> it = context.getRandomObjects(Hyip.class, howMany);
		List<Hyip> result = new ArrayList<Hyip>();
		Iterator<Player> iterator = it.iterator();
		for (int i = 0; i < howMany; i++) {
			result.add((Hyip) iterator.next());
		}
		return result;
	}

	private List<Hyip> chooseAllProducers() {
		Context<Player> context = ContextUtils.getContext(this);
		Iterable<Player> it = context.getObjects(Hyip.class);
		List<Hyip> result = new ArrayList<Hyip>();
		Iterator<Player> iterator = it.iterator();
		while (iterator.hasNext()) {
			result.add((Hyip) iterator.next());
		}
		return result;
	}

	public int getExpertise() {
		return expertise;
	}

	public RatingStrategy getRatingStrategy() {
		return ratingStrategy;
	}

}
