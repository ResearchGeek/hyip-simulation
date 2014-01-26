package CredibilityGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public abstract class Player {
	protected static Random random = new Random();
	private double gain;
	private Strategy strategy;
	
	public double getGain() {
		return gain;
	}

	public void setGain(double gain) {
		this.gain = gain;
	}
	
	public Strategy getStrategy() {
		return strategy;
	}
	
	public void setStrategy(Strategy strategy){
		this.strategy = strategy;
	}
/*
	public static void stochasticSampling(ArrayList<Player> population){
		if(population.size()==0)
			return;
		
		Collections.sort(population, new PlayerComparator());
		double min = population.get(population.size()-1).getGain();
		double scaling = min<0?((-1)*min):0;
		
		double maxRange = 0;
		ArrayList<Double> ranges = new ArrayList<Double>();
		ArrayList<Strategy> strategiesBackup = new ArrayList<Strategy>();
		for(Player p:population){
			maxRange += (p.getGain()+scaling);
			ranges.add(maxRange);
			strategiesBackup.add(p.getStrategy().copy());
		}
		double step = maxRange/population.size();
		double start = random.nextDouble()*step;
		for(int i=0; i<population.size(); i++){
			int selectedPlayer = population.size()-1;
			for(int j=0; j<ranges.size(); j++){
				double pointer = start+i*step;
				if(pointer<ranges.get(j)){
					selectedPlayer = j;
					break;
				}
			}
			population.get(i).getStrategy().copyStrategy(strategiesBackup.get(selectedPlayer));
		}
	}
*/
}
