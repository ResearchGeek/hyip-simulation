package CredibilityGame;

import java.util.ArrayList;
import java.util.Collections;

import repast.simphony.random.RandomHelper;
import HyipGame.ExitStrategy;

public abstract class Player {

	private double gain;
	private Strategy strategy;
	private ExitStrategy exitStrategy;

	public double getGain() {
		return gain;
	}

	public void setGain(double gain) {
		this.gain = gain;
	}

	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
	
	public ExitStrategy getExitStrategy() {
		return exitStrategy;
	}

	public void setExitStrategy(ExitStrategy exitStrategy) {
		this.exitStrategy = exitStrategy;
	}

	/**
	 * Evolution with Stochasting Universal Sampling, I modified little
	 * SUS method coded by Paulina and Grzegorz
	 * 
	 * @since 1.1
	 * @param population
	 */
	public static void stochasticSampling(ArrayList<Hyip> population) {
		if (population.size() == 0)
			return;

		Collections.sort(population, new PlayerComparator());
		double min = population.get(population.size() - 1).getCash();
		double scaling = min < 0 ? ((-1) * min) : 0;

		double maxRange = 0;
		ArrayList<Double> ranges = new ArrayList<Double>();
		ArrayList<ExitStrategy> strategiesBackup = new ArrayList<ExitStrategy>();
		ArrayList<EpPair> epBackup = new ArrayList<EpPair>();
		for (Hyip p : population) {
			maxRange += (p.getCash() + scaling);
			ranges.add(maxRange);
			strategiesBackup.add(p.getExitStrategy().copy());
			epBackup.add(p.getEpPair().copy());
		}
		double step = maxRange / population.size();
		double start = RandomHelper.nextDoubleFromTo(0, 1) * step;
		for (int i = 0; i < population.size(); i++) {
			int selectedPlayer = population.size() - 1;
			for (int j = 0; j < ranges.size(); j++) {
				double pointer = start + i * step;
				if (pointer < ranges.get(j)) {
					selectedPlayer = j;
					break;
				}
			}
			Hyip nextHyip = population.get(i);
			nextHyip.getExitStrategy()
					.copyStrategy(strategiesBackup.get(selectedPlayer));
			nextHyip.getEpPair()
				.copyStrategy(epBackup.get(selectedPlayer));
			nextHyip.mutate();
		}
	}

}
