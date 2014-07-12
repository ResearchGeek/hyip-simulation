package CredibilityGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import logger.PjiitOutputter;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.util.ContextUtils;
import HyipGame.HyipEvolve;

public class GameController {

	private int generationNumber;
	private int iterationNumber;
	private int currentGeneration;
	private int currentIteration;

	private DateTime previous = new DateTime();

	public GameController() {
		Parameters params = RunEnvironment.getInstance().getParameters();
		iterationNumber = (Integer) params.getValue("iteration_number");
		// variable iterationNumber states how many ticks long
		// is a single generation, we use mostly value of 200
		generationNumber = (Integer) params.getValue("generation_number");
		// how many generations we want to simulate
		// mostly it is 10 generations in our batch files
		say("generationNumber: " + generationNumber);
		say("iterationNumber: " + iterationNumber);
	}

	/**
	 * Tells whether this is a first generation (warming up) or not (generations
	 * are indexed starting from 0)
	 * 
	 * @return true if current generation is the first one
	 */
	public boolean isFirstGeneration() {
		return currentGeneration == 0;
	}

	@ScheduledMethod(start = 1.0, interval = 1.0, priority = -2000)
	public void firstStep() {
		if (isFirstGeneration()) {
			// warming up, hold on with evolution,
			// we want to guess the first strategies - entry level
			if (currentIteration == (iterationNumber - 1)) {
				say("counterIteration: " + currentIteration);
				say("Execute first generation end protocols");
				resetAllHyips();
				// reset all objects and states, fields, etc in a Hyip
				resetAllInvestors();
			}
		} else {
			if (currentIteration == (iterationNumber - 1)) {
				say("counterIteration: " + currentIteration);
				say("Execute generation end protocols");
				// start evolution of Hyips
				HyipEvolve.evolve(this);
				resetAllHyips();
				// reset all objects and states, fields, etc in a Hyip
				resetAllInvestors();
			}
		}
	}

	/**
	 * Resets states in all HYIPs, including the money in account, offers sold,
	 * calculated income, marketing state, etc. - except the exit strategy
	 */
	private void resetAllHyips() {
		say(Constraints.RESET_ALL_HYIPS_MESSAGE);
		Set<Hyip> allHyips = chooseAllProducers(this);
		say("Resetting all hyips, all together " + allHyips.size()
				+ " of them.");
		for (Hyip hyip : allHyips) {
			hyip.resetMe();
		}
	}

	/**
	 * Resets states in all Investors
	 */
	private void resetAllInvestors() {
		say(Constraints.RESET_ALL_INVESTORS_MESSAGE);
		List<Investor> allInvestors = chooseAllConsumers(this);
		say("Resetting all investors, all together " + allInvestors.size()
				+ " of them.");
		for (Investor investor : allInvestors) {
			investor.resetMe();
		}
	}

	@ScheduledMethod(start = 1.0, interval = 1.0, priority = -3000)
	public void step() {
		DateTime dateTime = new DateTime();
		Seconds seconds = Seconds.secondsBetween(previous, dateTime);
		Minutes minutes = Minutes.minutesBetween(previous, dateTime);
		say("It took " + minutes.getMinutes() + " minutes and "
				+ seconds.getSeconds() + " seconds between ticks.");

		// check whether this is the last generation/iteration
		if (currentIteration == (iterationNumber - 2)) {
			if (currentGeneration == (generationNumber - 1)) {
				say("Ending instance run");
				RunEnvironment.getInstance().endRun();
			}
		}

		if (currentIteration == (iterationNumber - 1)) {
			say("This is the last iteration in this gen");
			currentIteration = 0;
			say("Ending current generation");
			currentGeneration++;
		} else {
			say("Incrementing current iteration number to: "
					+ (currentIteration + 1));
			currentIteration++;
		}
		previous = new DateTime();
	}

	public int getCurrentGeneration() {
		return currentGeneration;
	}

	public int getCurrentIteration() {
		return currentIteration;
	}

	/**
	 * We check if simulation is warmed up, by comparing currentIteration to the
	 * threshold after which we believe first payouts and second invests are
	 * done
	 * 
	 * by default, threshold is = 200 * 0.05 equals 10 ticks
	 * 
	 * @return boolean
	 */
	public boolean isWarmedUp() {
		return currentIteration >= (iterationNumber * 0.05);
	}

	/**
	 * This method is used to get All HYIP-s which exist in the simulator
	 * 
	 * @param contextBeing
	 * @return ArrayList of all HYIP-s
	 */
	public static ArrayList<Hyip> chooseSpecificProducers(Object contextBeing,
			boolean good) {
		Context<Player> context = ContextUtils.getContext(contextBeing);
		Iterable<Player> it = good ? context.getObjects(GoodLookingHyip.class)
				: context.getObjects(BadLookingHyip.class);
		ArrayList<Hyip> result = new ArrayList<Hyip>();
		Iterator<Player> iterator = it.iterator();
		while (iterator.hasNext()) {
			result.add((Hyip) iterator.next());
		}
		return result;
	}

	public static HashSet<Hyip> chooseAllProducers(Object contextBeing) {
		Context<Player> context = ContextUtils.getContext(contextBeing);
		Iterable<Player> it = context.getObjects(Hyip.class);
		HashSet<Hyip> result = new HashSet<Hyip>();
		Iterator<Player> iterator = it.iterator();
		while (iterator.hasNext()) {
			result.add((Hyip) iterator.next());
		}
		return result;
	}

	public static ArrayList<Investor> chooseAllConsumers(Object contextBeing) {
		Context<Player> context = ContextUtils.getContext(contextBeing);
		Iterable<Player> it = context.getObjects(Investor.class);
		ArrayList<Investor> result = new ArrayList<Investor>();
		Iterator<Player> iterator = it.iterator();
		while (iterator.hasNext()) {
			result.add((Investor) iterator.next());
		}
		return result;
	}

	private static void say(String s) {
		PjiitOutputter.say(s);
	}

}
