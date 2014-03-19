package CredibilityGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import logger.PjiitOutputter;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import HyipGame.HyipEvolve;
import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.util.ContextUtils;

public class GameController {
	private int generationNumber;
	private int iterationNumber;
	private int currentGeneration;
	private int currentIteration;

	private DateTime previous = new DateTime();

	public GameController() {
		Parameters params = RunEnvironment.getInstance().getParameters();
		iterationNumber = (Integer) params.getValue("iteration_number");
		// zmienna oznacza po ilu tickach zakonczyn dany run
		// ustawione w jednym z batchy na 200
		generationNumber = (Integer) params.getValue("generation_number");
		// oznacza po ilu generacjach zakonczyc batch job
		// ustawione w jednym z batchy na 10
		say("generationNumber: " + generationNumber);
		say("iterationNumber: " + iterationNumber);
	}

	@ScheduledMethod(start = 1.0, interval = 1.0, priority = ScheduleParameters.FIRST_PRIORITY)
	public void firstStep() {
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
	
	/**
	 * Resets states in all Hyips - except a strategy
	 */
	private void resetAllHyips(){
		say("resetAllHyips() starts work. Choosing all producers (HYIPs).");
		List<Hyip> allHyips = chooseAllProducers(this);
		say("Resetting all hyips, all together " + allHyips.size() + " of them.");
		for(Hyip hyip : allHyips){
			hyip.resetMe();
		}
	}
	
	/**
	 * Resets states in all Investors
	 */
	private void resetAllInvestors(){
		say("resetAllInvestors() starts work. Choosing all consumers (Investors).");
		List<Investor> allInvestors = chooseAllConsumers(this);
		say("Resetting all investors, all together " + allInvestors.size() + " of them.");
		for(Investor investor : allInvestors){
			investor.resetMe();
		}
	}

	@ScheduledMethod(start = 1.0, interval = 1.0, priority = ScheduleParameters.LAST_PRIORITY)
	public void step() {
		DateTime dateTime = new DateTime();
		Seconds seconds = Seconds.secondsBetween(previous, dateTime);
		Minutes minutes = Minutes.minutesBetween(previous, dateTime);
		System.out.println("It took " + minutes.getMinutes() + " minutes and "
				+ seconds.getSeconds() + " seconds between ticks.");

		System.out.println("Let's calculate some ROI's");
		Hyip.calculateRois();

		// check whether this is the last generation/iteration
		if (currentIteration == (iterationNumber - 1)) {
			System.out.println("This is the last iteration");
			currentIteration = 0;
			if (currentGeneration == (generationNumber - 1)) {
				System.out.println("Ending instance run");
				RunEnvironment.getInstance().endRun();
			} else {
				// Hyip.evolve();
				System.out.println("Ending current generation");
				// Consumer.evolve();
				currentGeneration++;
			}
		} else {
			System.out.println("Incrementing current iteration number to: "
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
	 * threshold after wich we believ first payouts and second invests are done
	 * 
	 * by default, threshold is = 200 * 0.05 equals 10 ticks
	 * 
	 * @return boolean
	 */
	public boolean isWarmedUp() {
		return currentIteration >= (iterationNumber * 0.05);
	}
	
	/**
	 * This method is used to get All Hyips which exist in the simulator
	 * @param contextBeing
	 * @return ArrayList of all Hyips
	 */
	public static ArrayList<Hyip> chooseAllProducers(Object contextBeing) {
		Context<Player> context = ContextUtils.getContext(contextBeing);
		Iterable<Player> it = context.getObjects(Hyip.class);
		ArrayList<Hyip> result = new ArrayList<Hyip>();
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
	
	private void logActivity(String s) {
		PjiitOutputter.log(s);
	}
	
	private static void say(String s) {
		PjiitOutputter.say(s);
	}

}
